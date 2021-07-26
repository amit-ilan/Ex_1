package com.example.contactsapp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.test.core.app.ApplicationProvider;

import com.google.common.collect.Iterables;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
public class ContactsUnitTest {
    ContactViewModel viewModel;
    TestContactProvider testContactProvider;

    @Mock
    Context mockContext;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();

    @Before
    public void init(){ // TODO - is it before every test? wastefull to create new instances?
        viewModel = new ContactViewModel();
        testContactProvider = new TestContactProvider();
        viewModel.getContacts().observeForever(viewModelContacts -> {});
        //ApplicationProvider.getApplicationContext();
    }

    @Test
    public void getContacts() {
        viewModel.onListStart(mockContext, testContactProvider);
        assertTrue(Iterables.elementsEqual(viewModel.getContacts().getValue(), testContactProvider.getContacts(mockContext).values()));

//        for(Contact c:viewModel.getContacts().getValue()){
//            Log.d("TAG1", c.getName());
//            System.out.println(c.getName());
//        }
//        for(Contact c:testContactProvider.getContacts(mockContext).values()){
//            Log.d("TAG2", c.getName());
//        }
    }

    @Test
    public void getContactAfterHidden(){
        String removeId = testContactProvider.getContacts(mockContext).keySet().iterator().next();
        viewModel.onHideContact(removeId);
        for (Contact contact : viewModel.getContacts().getValue()){ // TODO more efficient way?
            assertTrue(!contact.getDeviceId().equals(removeId));
        }
    }
}

