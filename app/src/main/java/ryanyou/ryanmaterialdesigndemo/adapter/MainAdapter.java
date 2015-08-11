package ryanyou.ryanmaterialdesigndemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ryanyou.ryanmaterialdesigndemo.R;
import ryanyou.ryanmaterialdesigndemo.bean.TestBean;

/**
 * Created by RyanYou on 15/8/11.
 */
public class MainAdapter extends BaseAdapter {

    private Context context;
    private List<TestBean> mData;

    public MainAdapter(Context context,List<TestBean> mData){
        this.context = context;
        this.mData = (mData == null) ? new ArrayList<TestBean>() : mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public TestBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        TextView tv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_main_adapter,null);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.main_adapter_tv);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText("Item - " + getItem(position).content);
        return convertView;
    }
}
