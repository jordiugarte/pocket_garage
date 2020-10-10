package bo.com.golpistasElectricistas.pocketGarage;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import bo.com.golpistasElectricistas.pocketGarage.model.Base;
import bo.com.golpistasElectricistas.pocketGarage.model.User;
import bo.com.golpistasElectricistas.pocketGarage.repository.RepositoryImpl;
import bo.com.golpistasElectricistas.pocketGarage.repository.RepositoryMock;
import bo.com.golpistasElectricistas.pocketGarage.utils.Constants;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class LoginUnitTest {
    RepositoryImpl repository;

    @Before
    public void beforeEach() {
        repository = new RepositoryMock();
    }

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void loginFailed() {
        String email = "sergio@laguna.com";
        String password = "fffffff";
        LiveData<Base<User>> result = repository.login(email, password);
        assertNotNull(result);
        result.observeForever(new Observer<Base<User>>() {
            @Override
            public void onChanged(Base<User> userBase) {
                assertFalse(userBase.isSuccess());
                assertEquals(Constants.LOGIN_ERROR, userBase.getError());
            }
        });
    }
}
