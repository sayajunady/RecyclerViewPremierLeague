package com.junadhi.premierleague;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvClub;
    private ArrayList<Club> list = new ArrayList<>();
    ListClubAdapter listClubAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvClub = findViewById(R.id.rv_club);
        rvClub.setHasFixedSize(true);

        list.addAll(ClubData.getListData());
        showRecyclerList();
    }

    private void showRecyclerList(){
        rvClub.setLayoutManager(new LinearLayoutManager(this));
        ListClubAdapter listClubAdapter = new ListClubAdapter(list);
        rvClub.setAdapter(listClubAdapter);

        listClubAdapter.setOnItemClickCallback(new ListClubAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Club club) {
                Intent moveIntent = new Intent(MainActivity.this, DetailClubActivity.class);
                moveIntent.putExtra(DetailClubActivity.ITEM_EXTRA, club);
                startActivity(moveIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
              listClubAdapter.getFilter().filter(newText);
              return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}