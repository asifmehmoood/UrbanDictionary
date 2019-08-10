package com.asif.urbandictionary.observer;

import com.asif.urbandictionary.module.DictResponse;

import java.util.List;

public interface RepositoryObserver {
    void onUserDataChanged(List<DictResponse> list);
}