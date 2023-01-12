package com.example.dogimagesapi

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val isLoading = MutableLiveData<Boolean>()
    val isLoadingRuslan = MutableLiveData<Boolean>()
    val isError = MutableLiveData<Boolean>()
    val dogImage = MutableLiveData<DogImage>()



    fun loadDogImage() {
        val disposable = loadDogImageRx()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
//                isLoading.value = true
                isLoadingRuslan.value = true
            }
            .doAfterTerminate {
//                isLoading.value = false
                isLoadingRuslan.value = false
            }
            .doOnError {
                isError.value = true
            }
            .subscribe({
                run {
                    dogImage.value = it
                }
            }) {
                Log.d(TAG, "Error: ${it.message}")
            }
        compositeDisposable.add(disposable)
    }

    private fun loadDogImageRx(): Single<DogImage> {
        return ApiFactory.getApiService().loadDogImage()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


    companion object {
        private const val BASE_URL = "https://dog.ceo/api/breeds/image/random"
        private const val TAG = "MainViewModel"
    }
}