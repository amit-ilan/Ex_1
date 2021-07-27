package com.example.contactsapp;

import android.content.Context;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.google.common.collect.Iterables;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;

import java.util.HashMap;

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
    public void init(){
        viewModel = new ContactViewModel();
        testContactProvider = new TestContactProvider();
        viewModel.getContacts().observeForever(viewModelContacts -> {});
        viewModel.onListStart(mockContext, testContactProvider);
        //ApplicationProvider.getApplicationContext();
    }

    @Test
    public void equalsContacts(){
        Contact a = new Contact("A", "A@l.com", "111-111","","1");
        Contact b = new Contact("A", "A@l.com", "111-111","","1");
        assertEquals(a,b);
    }

    @Test
    public void getContacts() {
        assertTrue(Iterables.elementsEqual(viewModel.getContacts().getValue(), testContactProvider.getContacts(mockContext).values()));
    }

    @Test
    public void getContactAfterHidden(){
        HashMap<String, Contact> expected = testContactProvider.getContacts(mockContext);
        String removeId = expected.keySet().iterator().next();
        expected.remove(removeId);
        viewModel.onHideContact(removeId);
        assertTrue(Iterables.elementsEqual(viewModel.getContacts().getValue(), expected.values()));
    }

    @Test
    public void hideContactEmptyList(){
        ContactsProvider emptyContactsProvider = new EmptyContactProvider();
        viewModel.onListStart(mockContext, emptyContactsProvider);
        viewModel.onHideContact("FAKE_ID");
        assertTrue(viewModel.getContacts().getValue().size() == 0);
    }
}

