package com.example.sixthclass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity implements  ClickInterface {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    String[] richName;
    String[] topRich;
    List<Model> richList;
    Model model;
    MyAdapter adapter;
    String deletePerson = "";

    int[] image = {R.drawable.jeffbezos, R.drawable.billgates, R.drawable.bernard,
            R.drawable.warren, R.drawable.larry, R.drawable.amancio, R.drawable.zuckerberg};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        richName = getResources().getStringArray(R.array.Rich_Man);
        topRich = getResources().getStringArray(R.array.Top);
        recyclerView = findViewById(R.id.recycleId);
        swipeRefreshLayout = findViewById(R.id.swipeId);

        richList = new ArrayList<>();

        for (int i = 0; i < richName.length; i++) {
            model = new Model(image[i], richName[i], topRich[i]);
            richList.add(model);
        }

        adapter = new MyAdapter(richList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                model = new Model(R.drawable.billgates, "Bill Gates", "Top 2 Rich Man");
                richList.add(model);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP| ItemTouchHelper
            .END|ItemTouchHelper.DOWN|ItemTouchHelper.START,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {


            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(richList,fromPosition,toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition,toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            
            final int position= viewHolder.getAdapterPosition();
            if(direction== ItemTouchHelper.LEFT || direction== ItemTouchHelper.RIGHT ){

                deletePerson= richList.get(position).getName();
                final Model myModel= richList.get(position);
                richList.remove(position);
                adapter.notifyItemRemoved(position);

                Snackbar.make(recyclerView,deletePerson+" is Deleted",Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                richList.add(position,myModel);
                                adapter.notifyItemInserted(position);
                            }
                }).show();

            }

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorRed))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeLeftLabel("Delete")
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorRed))
                    .addSwipeRightActionIcon(R.drawable.ic_delete)
                    .addSwipeRightLabel("Delete")
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };




    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, ""+richList.get(position).getName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLongItemClick(final int position) {
        AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Delete").setIcon(R.drawable.ic_delete).setMessage("Do you want to delete ?")
                .setCancelable(true).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                richList.remove(position);
                adapter.notifyItemRemoved(position);

            }
        }).create().show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.menu_item,m);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.contactId){
            Intent intent = new Intent(MainActivity.this,ContactActivity.class);
            String message = "Contact";
            intent.putExtra("key", message);
            startActivity(intent);
        }
        if (item.getItemId()==R.id.aboutId){
            Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId()==R.id.settingId){
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}