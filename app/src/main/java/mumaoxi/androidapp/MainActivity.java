package mumaoxi.androidapp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mumaoxi.androidapp.model.App;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<App> apps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.listView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    App.writeToCSV(apps, "xiaomi.csv");
                } catch (Exception e) {

                }
                Snackbar.make(view, "数据已写入SDCard：xiaomi.csv", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        apps.addAll(App.getApplist(this));
        listView.setAdapter(new AppApdater(apps));
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class AppApdater extends BaseAdapter {
        List<App> apps;

        AppApdater(List<App> apps) {
            this.apps = apps;
        }

        @Override
        public int getCount() {
            return apps.size();
        }

        @Override
        public Object getItem(int i) {
            return apps.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            App app = apps.get(i);
            AppHolder holder;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item_app, null);
                holder = new AppHolder();
                holder.version = (TextView) view.findViewById(R.id.textViewVersion);
                holder.packageName = (TextView) view.findViewById(R.id.textViewPackageName);
                holder.name = (TextView) view.findViewById(R.id.textViewName);
                holder.icon = (ImageView) view.findViewById(R.id.imageView);
                view.setTag(holder);
            } else {
                holder = (AppHolder) view.getTag();
            }
            holder.icon.setImageDrawable(app.icon);
            holder.name.setText(app.name);
            holder.packageName.setText(app.packageName);
            holder.version.setText(app.versionName + "(" + app.versionCode + ")");
            return view;
        }
    }

    class AppHolder {
        ImageView icon;
        TextView name;
        TextView packageName;
        TextView version;
    }
}
