package io.coreflodev.httpcacheexample.chat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.coreflodev.httpcacheexample.R;
import io.coreflodev.httpcacheexample.api.ChatMessage;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatMessageViewHolder> {

    private static SimpleDateFormat MESSAGE_DATE_FORMAT
            = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.getDefault());
    private List<ChatMessage> messages;

    public ChatAdapter() {
        this.messages = new ArrayList<>();
    }

    @Override
    public ChatMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChatMessageViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(ChatMessageViewHolder holder, int position) {
        holder.setMessage(messages.get(position));
    }

    public void addMessages(List<ChatMessage> chatMessages) {
        int oldSize = messages.size();
        messages.clear();
        messages.addAll(chatMessages);
        notifyItemRangeChanged(0, oldSize);
        notifyItemRangeInserted(oldSize, chatMessages.size());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class ChatMessageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_chat_message_pseudo)
        TextView tvPseudo;
        @BindView(R.id.tv_chat_message_content)
        TextView tvContent;
        @BindView(R.id.tv_chat_message_date)
        TextView tvDate;

        public ChatMessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setMessage(ChatMessage chatMessage) {
            tvPseudo.setText(chatMessage.pseudo());
            tvDate.setText(MESSAGE_DATE_FORMAT.format(chatMessage.date()));
            tvContent.setText(chatMessage.message());
        }
    }
}
