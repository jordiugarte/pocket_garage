package bo.com.golpistasElectricistas.pocketGarage.repository;

import android.net.Uri;

import androidx.lifecycle.LiveData;

import java.util.List;

import bo.com.golpistasElectricistas.pocketGarage.model.Article;
import bo.com.golpistasElectricistas.pocketGarage.model.Base;
import bo.com.golpistasElectricistas.pocketGarage.model.User;

public interface RepositoryImpl {
    LiveData<Base<User>> login(String email, String password);

    Article getArticleItem(int id);

    LiveData<Base<List<Article>>> getFavorites();

    LiveData<Base<List<Article>>> getLastFiveArticles();

    LiveData<Base<List<Article>>> getArticles(int category);

    LiveData<Base<User>> register(User user, Uri photo);

    LiveData<Base<Article>> addArticle(Article article, List<Uri> photos);

    User getCurrentUser();

    void setCurrentUser(User user);

    void signOut();

    void addFavorite(Article article);

    void deleteFavorite(Article article);
}
