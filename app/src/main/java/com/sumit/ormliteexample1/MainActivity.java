package com.sumit.ormliteexample1;

import android.content.Context;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.sumit.ormliteexample1.db.dbhelper.PersonOpenDBHelper;
import com.sumit.ormliteexample1.db.dbmodel.Person;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Context context;
    Dao<Person, Long> personDao;
    int count = 0;
    TextView tv_person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_person = (TextView) findViewById(R.id.text);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPerson();
            }
        });

        initDB();
    }

    private void initDB() throws SQLException {
        PersonOpenDBHelper personOpenDBHelper = OpenHelperManager.getHelper(context, PersonOpenDBHelper.class);
        personDao = personOpenDBHelper.getDao();
    }

    private void addPerson() {

        Person person = new Person();
        person.setName(getRandomName() + " - " + count++);

        try {
            personDao.create(person);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        populateData(getPersons());
    }

    private List<Person> getPersons() {

        List<Person> personList = null;

        try {
            personList = personDao.queryForAll();

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return personList;
    }

    private void populateData(List<Person> personList) {

        String persons = null;

        if (personList != null)
            for (Person person : personList) {
                if (persons == null)
                    persons = person.getName();
                else persons += "\n" + person.getName();
            }

        if (persons != null)
            tv_person.setText(persons);
        else tv_person.setText("No records yet. Tap on '+' to add a record.");
    }

    private String getRandomName() {

        Random random = new Random();
        int low = 0;
        int high = 9;
        int result = random.nextInt(high - low) + low;

        if (result % 2 == 0)
            return "Superman";
        else return "Batman";
    }

    private void deleteAllRecords() {
        try {
            personDao.delete(personDao.queryForAll());
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        count = 0;
        populateData(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            deleteAllRecords();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
