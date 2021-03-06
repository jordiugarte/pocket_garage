package bo.com.golpistasElectricistas.pocketGarage.viewModel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import bo.com.golpistasElectricistas.pocketGarage.model.Base;
import bo.com.golpistasElectricistas.pocketGarage.model.User;
import bo.com.golpistasElectricistas.pocketGarage.repository.Repository;
import bo.com.golpistasElectricistas.pocketGarage.repository.RepositoryImpl;
import bo.com.golpistasElectricistas.pocketGarage.repository.RepositoryMock;

public class RegisterViewModel extends AndroidViewModel {
    private RepositoryImpl repository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<Base<User>> register(User user, Uri photo) {
        return repository.register(user, photo);
    }
}
