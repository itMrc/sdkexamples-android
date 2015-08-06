package com.easemob.applib.widget.chatrow;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.BufferType;

import com.easemob.EMCallBack;
import com.easemob.EMError;
import com.easemob.applib.widget.EMChatMessageList;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.easemob.chatuidemo.R;
import com.easemob.chatuidemo.activity.ContextMenu;
import com.easemob.chatuidemo.utils.SmileUtils;

public class EMChatRowText extends EMChatRow{

	private TextView contentView;

    public EMChatRowText(Context context, EMMessage message, int position, BaseAdapter adapter) {
		super(context, message, position, adapter);
	}

	@Override
	protected void onInflatView() {
		inflater.inflate(message.direct == EMMessage.Direct.RECEIVE ?
				R.layout.em_row_received_message : R.layout.em_row_sent_message, this);
	}

	@Override
	protected void onFindViewById() {
		contentView = (TextView) findViewById(R.id.tv_chatcontent);
	}

    @Override
    public void onSetUpView() {
        TextMessageBody txtBody = (TextMessageBody) message.getBody();
        Spannable span = SmileUtils.getSmiledText(context, txtBody.getMessage());
        // 设置内容
        contentView.setText(span, BufferType.SPANNABLE);

        if (message.direct == EMMessage.Direct.SEND) {
            switch (message.status) {
            case CREATE: 
                progressBar.setVisibility(View.VISIBLE);
                statusView.setVisibility(View.GONE);
                // 发送消息
                sendMsgInBackground(message);
                break;
            case SUCCESS: // 发送成功
                progressBar.setVisibility(View.GONE);
                statusView.setVisibility(View.GONE);
                break;
            case FAIL: // 发送失败
                progressBar.setVisibility(View.GONE);
                statusView.setVisibility(View.VISIBLE);
                break;
            case INPROGRESS: // 发送中
                progressBar.setVisibility(View.VISIBLE);
                statusView.setVisibility(View.GONE);
                break;
            default:
               break;
            }
        }
    }

    @Override
    protected void onUpdateView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onBuubleClick() {
        // TODO Auto-generated method stub
        
    }



}
