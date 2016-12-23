package com.jmedinilla.medinillamontaditos;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class ListAdapter extends ArrayAdapter<Product> {
    private Context context;
    static final int SHOW_ALL = 9879;
    static final int SHOW_FOOD = 6548;
    static final int SHOW_DRINK = 3217;
    static final int ORDER_RESET = 5554;
    static final int ORDER_NAME_ASC = 9515;
    static final int ORDER_NAME_DESC = 7536;
    static final int ORDER_TYPE_ASC = 1593;
    static final int ORDER_TYPE_DESC = 3572;
    static final int ORDER_QUANT_ASC = 4916;
    static final int ORDER_QUANT_DESC = 6841;

    ListAdapter(Context context) {
        super(context, R.layout.adapter_row, new ArrayList<>(Repository.getInstance()));
        this.context = context;
    }

    ListAdapter(Context context, ArrayList<Parcelable> list) {
        super(context, R.layout.adapter_row);
        this.context = context;
        this.clear();
        for (int i = 0; i < list.size(); i++) {
            this.add((Product) list.get(i));
        }
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        final CustomHolder holder;

        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.adapter_row, parent, false);
            holder = new CustomHolder();

            holder.txtName = (TextView) view.findViewById(R.id.row_txtName);
            holder.txtNumber = (TextView) view.findViewById(R.id.row_txtNumber);
            holder.btnUp = (ImageView) view.findViewById(R.id.row_btnUp);
            holder.btnDown = (ImageView) view.findViewById(R.id.row_btnDown);

            view.setTag(holder);
        } else {
            holder = (CustomHolder) view.getTag();
        }

        final Product product = getItem(position);
        if (product != null) {
            holder.txtName.setText(product.getName());
            holder.txtNumber.setText(String.valueOf(product.getQuantity()));
        }

        holder.btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (product != null) {
                    int value = product.getQuantity();
                    if (value == 10) {
                        Toast.makeText(context, "No puedes añadir más", Toast.LENGTH_SHORT).show();
                    } else {
                        product.setQuantity(value + 1);
                    }
                    holder.txtNumber.setText(String.valueOf(product.getQuantity()));
                }
            }
        });

        holder.btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (product != null) {
                    int value = product.getQuantity();
                    if (value == 0) {
                        Toast.makeText(context, "Ya hay 0 unidades", Toast.LENGTH_SHORT).show();
                    } else {
                        product.setQuantity(value - 1);
                    }
                    holder.txtNumber.setText(String.valueOf(product.getQuantity()));
                }
            }
        });

        return view;
    }

    void filterProducts(int filter) {
        switch (filter) {
            case SHOW_ALL:
                reloadProducts();
                break;
            case SHOW_FOOD:
                loadMontaditos();
                break;
            case SHOW_DRINK:
                loadBebidas();
                break;
            default:
                break;
        }
    }

    private void loadBebidas() {
        Repository repository = Repository.getInstance();
        this.clear();
        for (int i = 0; i < repository.size(); i++) {
            Product product = repository.get(i);
            if (product.getType() == Product.TYPE_DRINK) {
                add(product);
            }
        }
    }

    private void loadMontaditos() {
        Repository repository = Repository.getInstance();
        this.clear();
        for (int i = 0; i < repository.size(); i++) {
            Product product = repository.get(i);
            if (product.getType() == Product.TYPE_FOOD) {
                add(product);
            }
        }
    }

    private void reloadProducts() {
        this.clear();
        addAll(Repository.getInstance());
    }

    void orderProducts(int order) {
        switch (order) {
            case ORDER_RESET:
                reloadProducts();
                break;
            case ORDER_NAME_ASC:
                sort(Product.NAME_ASC);
                break;
            case ORDER_NAME_DESC:
                sort(Product.NAME_DESC);
                break;
            case ORDER_TYPE_ASC:
                sort(Product.TYPE_ASC);
                break;
            case ORDER_TYPE_DESC:
                sort(Product.TYPE_DESC);
                break;
            case ORDER_QUANT_ASC:
                sort(Product.QUANT_ASC);
                break;
            case ORDER_QUANT_DESC:
                sort(Product.QUANT_DESC);
                break;
            default:
                break;
        }
    }

    private class CustomHolder {
        TextView txtName;
        ImageView btnUp;
        TextView txtNumber;
        ImageView btnDown;
    }
}
