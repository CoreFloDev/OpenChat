package io.coreflodev.httpcacheexample.chat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.coreflodev.httpcacheexample.R;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<ChatMessage> messages;

    private static SimpleDateFormat MESSAGE_DATE_FORMAT
            = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.getDefault());

    public ChatAdapter(List<ChatMessage> messages) {
        this.messages = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setMessage(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_chat_message_pseudo)
        TextView tvPseudo;
        @BindView(R.id.tv_chat_message_content)
        TextView tvContent;
        @BindView(R.id.tv_chat_message_date)
        TextView tvDate;

        public ViewHolder(View itemView) {
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
