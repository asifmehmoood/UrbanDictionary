package com.asif.urbandictionary.activities;

import android.os.Bundle;


import com.asif.urbandictionary.R;

import com.asif.urbandictionary.adapter.DictionaryAdapter;
import com.asif.urbandictionary.network.HTTPRequestManager;
import com.asif.urbandictionary.module.DictResponse;
import com.asif.urbandictionary.observer.RepositoryObserver;
import com.asif.urbandictionary.observer.Subject;
import com.asif.urbandictionary.persistence.SharedPreferenceManager;
import com.asif.urbandictionary.utils.AppConstants;
import com.asif.urbandictionary.utils.CommonUtils;
import com.asif.urbandictionary.utils.DividerItemDecoration;

import butterknife.BindView;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RepositoryObserver {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Subject subject;
    List<DictResponse> dRespList = new ArrayList<DictResponse>();

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    DictionaryAdapter dictionaryAdapter;
    LinearLayoutManager layoutManager;
    @BindView(R.id.buttonSearch)
    Button buttonSearch;
    @BindView(R.id.editTextWord)
    EditText editTextWord;

    String wordToSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setUp();
        setOnclickButtonSearch();
    }

    private void setUp() {
        //register observer for change in dictionary
        subject = HTTPRequestManager.getHTTPRequestManager(getApplicationContext());
        subject.registerObserver(this);
        // set adapter values
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider_drawable);
        recyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        dictionaryAdapter = new DictionaryAdapter(new ArrayList<DictResponse>());
        dictionaryAdapter.refreshItems(dRespList);
        recyclerView.setAdapter(dictionaryAdapter);
    }

    private void setOnclickButtonSearch() {

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtils.hideKeyBaord(view, getApplicationContext());
                wordToSearch = editTextWord.getText().toString();

                if (wordToSearch.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please enter some word to search definition", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    CommonUtils.showLoading(MainActivity.this);
                    getDefinitionOfWord(wordToSearch);
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

//        menu.findItem(R.id.order_by_thumb_up).setTitle(Html.fromHtml("<font color='#ff3824'>Settings</font>"));


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.order_by_thumb_up:
                SharedPreferenceManager.getInstance(getApplicationContext()).storeOrderByStatus(AppConstants.THUMB.ORDER_BY_UP);
                orderList();
                notifyAdapterToChangeData();
                return true;
            case R.id.order_by_thumb_down:
                SharedPreferenceManager.getInstance(getApplicationContext()).storeOrderByStatus(AppConstants.THUMB.ORDER_BY_DOWN);
                orderList();
                notifyAdapterToChangeData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Todo: need to change color of dorpdown menue depends on sorting order
    private void setThumbUp(MenuItem item){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            item.findItem(R.id.order_by_thumb_up).setTitle(Html.fromHtml("<font color='#ff3824'>Settings</font>"));
//
//            Html.fromHtml(getResources().getString(R.string.order_by_thumb_down_html_green), Html.FROM_HTML_MODE_LEGACY);
//            Html.fromHtml(getResources().getString(R.string.order_by_thumb_up_html_black), Html.FROM_HTML_MODE_LEGACY);
//        } else {
//            Html.fromHtml(getResources().getString(R.string.order_by_thumb_down_html_green));
//            Html.fromHtml(getResources().getString(R.string.order_by_thumb_up_html_black));
//        }
    }

    /**
     * notify dataset changed and load all data again
     */
    private void notifyAdapterToChangeData() {
        dictionaryAdapter.refreshItems(dRespList);
        dictionaryAdapter.notifyDataSetChanged();
    }

    /**
     * order list of dictionary response depends on thumbs up or down
     */
    public void orderList() {
        int orderBy = SharedPreferenceManager.getInstance(getApplicationContext()).retrieveOrderByStatus();
        Log.d(TAG,"Order by value is "+orderBy);
        if(AppConstants.THUMB.ORDER_BY_UP == orderBy){
            sortThumbsUp();
        }else if(AppConstants.THUMB.ORDER_BY_DOWN == orderBy){
            sortThumbsDown();
        }else{
            Log.d(TAG,"no need to set order");
        }
    }

    /**
     * sort Descending Order thumbs Up
     */
    private void sortThumbsUp(){
        Collections.sort(dRespList, new Comparator<DictResponse>() {
            @Override
            public int compare(DictResponse o1, DictResponse o2) {
                return (int) (o2.thumbs_up - o1.thumbs_up);
            }
        });
    }

    /**
     * sort Descending Order thumbs down
     */
    private void sortThumbsDown(){
        Collections.sort(dRespList, new Comparator<DictResponse>() {
            @Override
            public int compare(DictResponse o1, DictResponse o2) {
                return (int) (o2.thumbs_down - o1.thumbs_down);
            }
        });
    }

    /**
     *
     * @param word to search from API
     */
    private void getDefinitionOfWord(String word) {
        HTTPRequestManager.getHTTPRequestManager(getApplicationContext()).getWordDefinitionAPI(word);
    }

    @Override
    public void onUserDataChanged(List<DictResponse> list) {
        if(null == list || list.size()==0){
            Log.i(TAG,"response is null");
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.empty_screen),Toast.LENGTH_SHORT).show();

        }
        if (dRespList != null) {
            dRespList.clear();
            dRespList.addAll(list);
        } else {
            dRespList = new ArrayList<>();
            dRespList.addAll(list);
        }
        orderList();
        CommonUtils.hideLoading();
        notifyAdapterToChangeData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subject.removeObserver(MainActivity.this);
    }

}