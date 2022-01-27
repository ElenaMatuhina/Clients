package com.example.clientlist;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.clientlist.database.Client;
import com.example.clientlist.database.DataAdapter;
import com.example.clientlist.database.DataBase;
import com.example.clientlist.database.Executer;
import com.example.clientlist.settings.SettingsActivity;
import com.example.clientlist.utils.Constans;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientlist.databinding.ActivityMainBinding;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private ActivityMainBinding binding;
    private DataBase dataBase;
    private DataAdapter dataAdapter;
    private RecyclerView recyclerView;
    private List<Client> listClient;
    private DrawerLayout drawerLayout;
    private DataAdapter.AdapterOnItemClicked adapterOnItemClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        setSupportActionBar(binding.appBarMain.toolbar);
        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);
        drawerLayout = findViewById(R.id.drawer_layout);

        adapterOnItemClicked = new DataAdapter.AdapterOnItemClicked() {
            @Override
            public void onAdapterItemClicked(int position) {
                Intent intent = new Intent(MainActivity.this, EditClientActivity.class);
                intent.putExtra(Constans.FIRST_NAME_KEY, listClient.get(position).getFirstName());
                intent.putExtra(Constans.LAST_NAME_KEY, listClient.get(position).getLastName());
                intent.putExtra(Constans.TEL_KEY, listClient.get(position).getTelepfone());
                intent.putExtra(Constans.NOTE_KEY, listClient.get(position).getDescription());
                intent.putExtra(Constans.TYPE_KEY, listClient.get(position).getType());
                intent.putExtra(Constans.VIP_KEY, listClient.get(position).getVip());
                intent.putExtra(Constans.ID_KEY, listClient.get(position).getId());
                startActivity(intent);
            }
        };
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(MainActivity.this, EditClientActivity.class);
              startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.id_search).getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Executer.getInstance().getDiscIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        listClient = dataBase.clientDao().getClientName(s);
                        Executer.getInstance().getMainIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                if(dataAdapter != null) {
                                    dataAdapter.updateAdapter(listClient);
                                }
                            }
                        });
                    }
                });
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.id_client) {
            Executer.getInstance().getDiscIO().execute(new Runnable() {
                @Override
                public void run() {
                    listClient = dataBase.clientDao().getClientList();
                    Executer.getInstance().getMainIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            if(dataAdapter != null) {
                                dataAdapter.updateAdapter(listClient);
                            }
                        }
                    });
                }
            });
        }
        else if(id == R.id.id_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.id_vip) {
            Executer.getInstance().getDiscIO().execute(new Runnable() {
                @Override
                public void run() {
                    listClient = dataBase.clientDao().getClientListVip();
                    Executer.getInstance().getMainIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            if(dataAdapter != null) {
                                dataAdapter.updateAdapter(listClient);
                            }
                        }
                    });
                }
            });
        }
        else if(id == R.id.id_important) {
            Executer.getInstance().getDiscIO().execute(new Runnable() {
                @Override
                public void run() {
                    listClient = dataBase.clientDao().getClientImpotance(0);
                    Executer.getInstance().getMainIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            if(dataAdapter != null) {
                                dataAdapter.updateAdapter(listClient);
                            }
                        }
                    });
                }
            });
        }
        else if(id == R.id.id_standart) {
            Executer.getInstance().getDiscIO().execute(new Runnable() {
                @Override
                public void run() {
                    listClient = dataBase.clientDao().getClientImpotance(1);
                    Executer.getInstance().getMainIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            if(dataAdapter != null) {
                                dataAdapter.updateAdapter(listClient);
                            }
                        }
                    });
                }
            });
        }
        else if(id == R.id.id_no_important) {
            Executer.getInstance().getDiscIO().execute(new Runnable() {
                @Override
                public void run() {
                    listClient = dataBase.clientDao().getClientImpotance(2);
                    Executer.getInstance().getMainIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            if(dataAdapter != null) {
                                dataAdapter.updateAdapter(listClient);
                            }
                        }
                    });
                }
            });
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void init() {
        recyclerView = findViewById(R.id.rvClient);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//по умолчанию - вертикальный recycler
        dataBase = (DataBase) DataBase.getInstance(getApplicationContext());
        listClient = new ArrayList<>();
        dataAdapter = new DataAdapter(listClient, adapterOnItemClicked, this);
        recyclerView.setAdapter(dataAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
        Executer.getInstance().getDiscIO().execute(new Runnable() {
            @Override
            public void run() {
                listClient = dataBase.clientDao().getClientList();
                Executer.getInstance().getMainIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(dataAdapter != null) {
                            dataAdapter.updateAdapter(listClient);
                        }
                    }
                });
            }
        });
    }



}