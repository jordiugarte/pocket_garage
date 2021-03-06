package bo.com.golpistasElectricistas.pocketGarage.repository;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bo.com.golpistasElectricistas.pocketGarage.model.Article;
import bo.com.golpistasElectricistas.pocketGarage.model.Base;
import bo.com.golpistasElectricistas.pocketGarage.model.User;
import bo.com.golpistasElectricistas.pocketGarage.utils.Constants;
import bo.com.golpistasElectricistas.pocketGarage.utils.Validations;

public class RepositoryMock implements RepositoryImpl {

    protected List<User> getMockUsers() {
        List<User> mockUsers = new ArrayList<>();
        /*mockUsers.add(new User("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSIxyT0DAa5_kwzb-e-bpTvAXIyW0OispA76Q&usqp=CAU", "1", "jordi@ugarte.com", "ffffffff", "Jordi", "Ugarte", "01/01/1999", 7889825, 10000));
        mockUsers.add(new User("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSIxyT0DAa5_kwzb-e-bpTvAXIyW0OispA76Q&usqp=CAU", "2", "ignacio@delrio.com", "ffffffff", "Ignacio", "del Rio", "01/01/1999", 77777777, 10000));
        mockUsers.add(new User("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSIxyT0DAa5_kwzb-e-bpTvAXIyW0OispA76Q&usqp=CAU", "3", "sergio@laguna.com", "ffffffff", "Sergio", "Laguna", "01/01/1999", 77777777, 10000));
        */
        return mockUsers;
    }

    @Override
    public LiveData<Base<User>> login(String email, String password) {
        MutableLiveData<Base<User>> result = new MutableLiveData<>();
        if (email.isEmpty() || password.isEmpty()) {
            result.postValue(new Base(Constants.EMPTY_VALUE_ERROR, null));
            return result;
        }
        if (!Validations.emailIsValid(email)) {
            result.postValue(new Base(Constants.INVALID_EMAIL_ERROR, null));
            return result;
        }
        for (User user : getMockUsers()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                result.postValue(new Base(user));
                return result;
            } else if (user.getEmail().equals(email)) {
                result.postValue(new Base(Constants.INCORRECT_PASSWORD_ERROR, null));
                return result;
            }
        }
        result.postValue(new Base(Constants.INCORRECT_EMAIL_ERROR, null));
        return result;
    }

    @Override
    public Article getArticleItem(int id) {
        return null;
    }

    @Override
    public LiveData<Base<List<Article>>> getFavorites() {
        return null;
    }

    @Override
    public LiveData<Base<List<Article>>> getLastFiveArticles() {
        return null;
    }

    @Override
    public LiveData<Base<List<Article>>> getArticles(int category) {
        return null;
    }

    @Override
    public LiveData<Base<User>> register(User user, Uri uri) {/*
        MutableLiveData<Base<User>> result = new MutableLiveData<>();
        if (ci.isEmpty() || email.isEmpty() || pass.isEmpty() || name.isEmpty() || lastName.isEmpty() || date.isEmpty() || phone.isEmpty()) {
            result.postValue(new Base(Constants.EMPTY_VALUE_ERROR, null));
            return result;
        }
        if (!Validations.emailIsValid(email)) {
            result.postValue(new Base(Constants.INVALID_EMAIL_ERROR, null));
            return result;
        }
        if (!Validations.nameIsValid(name)) {
            result.postValue(new Base<>(Constants.INVALID_NAME_ERROR, null));
            return result;
        }
        if (!Validations.nameIsValid(lastName)) {
            result.postValue(new Base<>(Constants.INVALID_LAST_NAME_ERROR, null));
            return result;
        }
        try {
            String ciint = ci;
            int phoneint = Integer.parseInt(phone);
            for (User user : getMockUsers()) {
                if (ciint == user.getCi()) {
                    result.postValue(new Base<>(Constants.REPEATED_CI_ERROR, null));
                    return result;
                }
                if (email.equals(user.getEmail())) {
                    result.postValue(new Base<>(Constants.REPEATED_EMAIL_ERROR, null));
                    return result;
                }
            }
            User user = new User(photo, ciint, email, pass, name, lastName, date, phoneint, 10000);
            result.postValue(new Base<>(user));
            return result;
        } catch (final NumberFormatException e) {
            result.postValue(new Base<>(Constants.INVALID_CI_ERROR, null));
            return result;
        }*/
        return null;
    }

    @Override
    public LiveData<Base<Article>> addArticle(Article article, List<Uri> photos) {
        return null;
    }

    @Override
    public User getCurrentUser() {
        return null;
    }

    @Override
    public void setCurrentUser(User user) {

    }

    @Override
    public void signOut() {

    }

    @Override
    public void addFavorite(Article article) {

    }

    @Override
    public void deleteFavorite(Article article) {

    }
}