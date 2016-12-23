package com.jmedinilla.medinillamontaditos;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Preferences.getInstance(ListActivity.this).loadMontaditos();

        if (savedInstanceState == null) {
            listAdapter = new ListAdapter(ListActivity.this);
        } else {
            listAdapter = new ListAdapter(ListActivity.this, savedInstanceState.getParcelableArrayList("key_actual_list"));
        }

        ListView montadito_list = (ListView) findViewById(R.id.montadito_list);
        Button montadito_button_ok = (Button) findViewById(R.id.montadito_button_ok);
        Button montadito_button_filter = (Button) findViewById(R.id.montadito_button_filter);
        montadito_list.setAdapter(listAdapter);
        montadito_list.setDivider(null);

        montadito_button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAndReset();
            }
        });

        montadito_button_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterDialog();
            }
        });
    }

    private void showAndReset() {
        Repository repository = Repository.getInstance();
        String result = "";
        for (int i = 0; i < repository.size(); i++) {
            Product product = repository.get(i);
            if (product.getQuantity() != 0) {
                result += product.getName() + " (x" + String.valueOf(product.getQuantity()) + ")";
                if (i != (repository.size() - 1)) {
                    result += "\n";
                }
            }
        }

        Intent intent = new Intent(ListActivity.this, ResultActivity.class);
        intent.putExtra("result_extra", result);
        startActivity(intent);

        for (int i = 0; i < repository.size(); i++) {
            Product product = repository.get(i);
            product.setQuantity(0);
        }
        listAdapter.notifyDataSetChanged();
    }

    private void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
        builder.setTitle("Filter");
        builder.setItems(new String[]{"All", "Food", "Drinks"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        listAdapter.filterProducts(ListAdapter.SHOW_ALL);
                        break;
                    case 1:
                        listAdapter.filterProducts(ListAdapter.SHOW_FOOD);
                        break;
                    case 2:
                        listAdapter.filterProducts(ListAdapter.SHOW_DRINK);
                        break;
                }
            }
        });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_order_name:
                listAdapter.orderProducts(ListAdapter.ORDER_NAME_ASC);
                break;
            case R.id.menu_order_name_desc:
                listAdapter.orderProducts(ListAdapter.ORDER_NAME_DESC);
                break;
            case R.id.menu_order_type:
                listAdapter.orderProducts(ListAdapter.ORDER_TYPE_ASC);
                break;
            case R.id.menu_order_type_desc:
                listAdapter.orderProducts(ListAdapter.ORDER_TYPE_DESC);
                break;
            case R.id.menu_order_quant:
                listAdapter.orderProducts(ListAdapter.ORDER_QUANT_ASC);
                break;
            case R.id.menu_order_quant_desc:
                listAdapter.orderProducts(ListAdapter.ORDER_QUANT_DESC);
                break;
            case R.id.menu_order_reset:
                listAdapter.orderProducts(ListAdapter.ORDER_RESET);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Preferences.getInstance(ListActivity.this).saveMontaditos();
        ArrayList<Product> actualList = new ArrayList<>();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            actualList.add(listAdapter.getItem(i));
        }
        outState.putParcelableArrayList("key_actual_list", actualList);
    }
}
