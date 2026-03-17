package com.simats.cdss.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.simats.cdss.R;
import com.simats.cdss.models.StaffDashboardResponse;
import java.util.List;

public class ReassessmentAdapter extends RecyclerView.Adapter<ReassessmentAdapter.ViewHolder> {

    private final List<StaffDashboardResponse.ReassessmentItem> items;
    private final Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(StaffDashboardResponse.ReassessmentItem item);
    }

    public ReassessmentAdapter(Context context, List<StaffDashboardResponse.ReassessmentItem> items) {
        this.context = context;
        this.items = items;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reassessment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StaffDashboardResponse.ReassessmentItem item = items.get(position);

        // Set title based on type
        if ("SpO2".equalsIgnoreCase(item.getType())) {
            holder.tvTitle.setText("SpO2 Check Due");
            holder.iconBg.setBackgroundResource(R.drawable.bg_icon_amber);
            holder.ivIcon.setImageResource(R.drawable.ic_clock);
            holder.ivIcon.setColorFilter(context.getColor(R.color.amber_700));
        } else {
            holder.tvTitle.setText("ABG Sample Required");
            holder.iconBg.setBackgroundResource(R.drawable.bg_icon_blue);
            holder.ivIcon.setImageResource(R.drawable.ic_description);
            holder.ivIcon.setColorFilter(context.getColor(R.color.blue_600));
        }

        // Set patient info
        holder.tvSubtext.setText(item.getPatientName() + " • Bed " + item.getBedNumber());

        // Set time status
        int dueIn = item.getDueIn();
        if (dueIn < 0) {
            holder.tvTime.setText("Overdue by " + Math.abs(dueIn) + " mins");
            holder.tvTime.setTextColor(context.getColor(R.color.red_500));
        } else {
            holder.tvTime.setText("Due in " + dueIn + " mins");
            holder.tvTime.setTextColor(context.getColor(R.color.text_secondary));
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View iconBg;
        ImageView ivIcon;
        TextView tvTitle, tvSubtext, tvTime;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconBg = itemView.findViewById(R.id.icon_bg);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvSubtext = itemView.findViewById(R.id.tv_subtext);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }
}
