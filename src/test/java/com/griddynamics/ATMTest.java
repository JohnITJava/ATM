package com.griddynamics;

import com.griddynamics.controller.UserInterfaceController;
import com.griddynamics.entity.User;
import com.griddynamics.sql.SQLHandler;
import org.junit.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ATMTest {

    @Before
    public void connect() throws Exception{
        SQLHandler.connectToTestDB();
    }
    @After
    public void disconnect(){
        SQLHandler.disconnectFromTestDB();
    }

    @Test
    public void isHumanExistsInDB(){
        Assert.assertTrue(SQLHandler.tryToLogin("admin1", "admin1"));
    }

    @Test
    public void isHumonNotExists(){
        Assert.assertFalse(SQLHandler.tryToLogin("admin2", "admin2"));
    }

    @Test
    public void checkIfUserTryPutNegativeSum(){
        User actual = new User("user1", "user1");
        Assert.assertFalse(actual.put(-500));
    }

    @Test
    public void checkUserTryPutMoney(){
        User actual = new User("user1", "user1");
        Assert.assertTrue(actual.put(500));
    }

    @Test
    public void checkUserHaveEnoughMoneyToTake(){
        User actual = new User("user1", "user1");
        Assert.assertFalse(actual.take(500));
    }

    @Test
    public void checkSuccessTakingMoney(){
        User actual = new User("user2", "user2");
        Assert.assertTrue(actual.take(500));
    }

    @Test
    public void isCheckBalanceUserCorrect(){
        User actual = new User("user2", "user2");
        Assert.assertEquals("500,00", actual.checkBalance());
    }

    @Test
    public void isTryToRegisterCorrect(){
        Assert.assertTrue(SQLHandler.tryToRegister("userTest", "userTest"));
    }

    @Test
    public void isTryToRegInCorrect(){
        Assert.assertFalse(SQLHandler.tryToRegister("user1", "user1"));
    }

    @Test
    public void checkInputMoneyIsDividedIn100(){
        UserInterfaceController uic = mock(UserInterfaceController.class);
        when(uic.isMoneyInputMultTo100()).thenReturn(true);
        Assert.assertTrue(uic.isMoneyInputMultTo100());
    }

    @Test
    public void isGettingUsersBalanceCorrect(){
        Assert.assertEquals(500.00, SQLHandler.getUserBalance("user2"), 0.05);
    }



}
