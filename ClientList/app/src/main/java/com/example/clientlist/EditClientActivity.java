package com.example.clientlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.clientlist.database.Client;
import com.example.clientlist.database.DataBase;
import com.example.clientlist.database.Executer;
import com.example.clientlist.databinding.EditClientBinding;
import com.example.clientlist.utils.Constans;

import static com.example.clientlist.databinding.EditClientBinding.inflate;

//возможность редактировать поля, закрывать активити после сохранения, добавляем кнопку делет, добавляем алерт диалог, функция для обновления ресайклвью
public class EditClientActivity extends AppCompatActivity {
    private DataBase dataBase;
    private EditClientBinding binding;
    private EditText edCompany, edFirstName, edLastName, edTel, edNotes;
    private CheckBox checkBoxImpot, checkBoxStandart, checkBoxNoImpot, checkBoxVip;
    private int impotance = 3;
    private int vip = 0;
    private CheckBox checkBoxArray[] = new CheckBox[3];
    private boolean isEdit = false;
    private boolean newUser = false;
    private int idClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        getIntentCurrentClient();


        binding.btSaveClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImpotance();
                if (!TextUtils.isEmpty(edCompany.getText().toString())
                        &&!TextUtils.isEmpty(edFirstName.getText().toString())
                        && !TextUtils.isEmpty(edLastName.getText().toString())
                        && !TextUtils.isEmpty(edTel.getText().toString())
                        && !TextUtils.isEmpty(edNotes.getText().toString())) {
                    Executer.getInstance().getDiscIO().execute(new Runnable() {//создаем второстепенный поток
                        @Override
                        public void run() {
                            if(isEdit) {
                                Client client = new Client(edCompany.getText().toString(),
                                        edFirstName.getText().toString(),
                                        edLastName.getText().toString(),
                                        edTel.getText().toString(),
                                        impotance, edNotes.getText().toString(), vip);
                                client.setId(idClient);
                                dataBase.clientDao().updateClient(client);
                                finish();
                            } else {
                                Client client = new Client(edCompany.getText().toString(),
                                        edFirstName.getText().toString(),
                                        edLastName.getText().toString(),
                                        edTel.getText().toString(),
                                        impotance, edNotes.getText().toString(), vip);
                                dataBase.clientDao().insertClient(client);
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }

    private void init() {
        dataBase = (DataBase) DataBase.getInstance(getApplicationContext());
        edCompany = binding.edCompany;
        edFirstName = binding.edFirstName;
        edLastName = binding.edLastName;
        edNotes = binding.edNotes;
        edTel = binding.edTel;

        checkBoxImpot = binding.checkBoxImpot;
        checkBoxStandart = binding.checkBoxStandart;
        checkBoxNoImpot = binding.checkBoxNoImpot;
        checkBoxVip = binding.checkBoxVip;
        checkBoxArray[0] = checkBoxImpot;
        checkBoxArray[1] = checkBoxStandart;
        checkBoxArray[2] = checkBoxNoImpot;

    }

    public void onClickChImpot(View view) {
        checkBoxStandart.setChecked(false);
        checkBoxNoImpot.setChecked(false);
    }

    public void onClickChStandart(View view) {
        checkBoxImpot.setChecked(false);
        checkBoxNoImpot.setChecked(false);
    }

    public void onClickChNoImpot(View view) {
        checkBoxImpot.setChecked(false);
        checkBoxStandart.setChecked(false);
    }



    private void getIntentCurrentClient() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getStringExtra(Constans.FIRST_NAME_KEY) != null) {
                setIsEdit(false);
                edFirstName.setText(intent.getStringExtra(Constans.FIRST_NAME_KEY));
                edLastName.setText(intent.getStringExtra(Constans.LAST_NAME_KEY));
                edTel.setText(intent.getStringExtra(Constans.TEL_KEY));
                edNotes.setText(intent.getStringExtra(Constans.NOTE_KEY));
                checkBoxArray[intent.getIntExtra(Constans.TYPE_KEY, 0)].setChecked(true);
                idClient = intent.getIntExtra(Constans.ID_KEY, 0);
                if (intent.getIntExtra(Constans.VIP_KEY, 0) == 1) checkBoxVip.setChecked(true);
                newUser = false;

            } else {
                newUser = true;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!newUser) getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.edit_client) {
            setIsEdit(true);
        } else if(id == R.id.delete_client) {
            deleteDialog();
        }
        return true;
    }

    private void setIsEdit(boolean isEdit) {

        if(isEdit) {
            binding.btSaveClient.show();
        } else {
            binding.btSaveClient.hide();
        }

        this.isEdit = isEdit;

        edFirstName.setClickable(isEdit);
        edLastName.setClickable(isEdit);
        edTel.setClickable(isEdit);
        edNotes.setClickable(isEdit);

        edFirstName.setFocusable(isEdit);
        edLastName.setFocusable(isEdit);
        edTel.setFocusable(isEdit);
        edNotes.setFocusable(isEdit);

        edFirstName.setFocusableInTouchMode(isEdit);
        edLastName.setFocusableInTouchMode(isEdit);
        edTel.setFocusableInTouchMode(isEdit);
        edNotes.setFocusableInTouchMode(isEdit);

        checkBoxVip.setClickable(isEdit);
        checkBoxImpot.setClickable(isEdit);
        checkBoxStandart.setClickable(isEdit);
        checkBoxNoImpot.setClickable(isEdit);

    }


    private void getImpotance() {
        if (checkBoxImpot.isChecked()) {
            impotance = 0;
        } else if (checkBoxStandart.isChecked()) {
            impotance = 1;
        } else if (checkBoxNoImpot.isChecked()) {
            impotance = 2;
        }
        if (checkBoxVip.isChecked()) {
            vip = 1;
        }
    }


    private void deleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_message);
        builder.setTitle(R.string.delete_client);
        builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Executer.getInstance().getDiscIO().execute(new Runnable() {//создаем второстепенный поток
                    @Override
                    public void run() {
                            Client client = new Client(edCompany.getText().toString(),
                                    edFirstName.getText().toString(),
                                    edLastName.getText().toString(),
                                    edTel.getText().toString(),
                                    impotance, edNotes.getText().toString(), vip);
                            client.setId(idClient);
                            dataBase.clientDao().deleteClient(client);
                            finish();
                    }
                });
            }
        });


        builder.setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();

    }
}