package com.example.humania;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder> {

    private List<Conversation> conversationList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Conversation conversation);
    }

    public ConversationAdapter(List<Conversation> conversationList, OnItemClickListener listener) {
        this.conversationList = conversationList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation, parent, false);
        return new ConversationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        Conversation conversation = conversationList.get(position);
        holder.bind(conversation, listener);
    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }

    static class ConversationViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvLastMessage, tvTime, tvAvatarEmoji;

        public ConversationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvConvName);
            tvLastMessage = itemView.findViewById(R.id.tvConvLastMessage);
            tvTime = itemView.findViewById(R.id.tvConvTime);
            tvAvatarEmoji = itemView.findViewById(R.id.tvConvAvatarEmoji);
        }

        public void bind(Conversation conversation, OnItemClickListener listener) {
            tvName.setText(conversation.getOtherUserName() != null ? conversation.getOtherUserName() : "User");
            tvLastMessage.setText(conversation.getLastMessage());
            
            // Basic formatting for timestamp or just show "Just now"
            tvTime.setText("Now"); 
            
            itemView.setOnClickListener(v -> listener.onItemClick(conversation));
        }
    }
}
