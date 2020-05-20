package com.mall.sls.message;


import dagger.Module;
import dagger.Provides;

/**
 * @author jwc on 2020/5/19.
 * 描述：
 */
@Module
public class MessageModule {
    private MessageContract.MsgTypeView msgTypeView;
    private MessageContract.MsgInfoView  msgInfoView;

    public MessageModule(MessageContract.MsgTypeView msgTypeView) {
        this.msgTypeView = msgTypeView;
    }

    public MessageModule(MessageContract.MsgInfoView msgInfoView) {
        this.msgInfoView = msgInfoView;
    }

    @Provides
    MessageContract.MsgTypeView provideMsgTypeView(){
        return msgTypeView;
    }

    @Provides
    MessageContract.MsgInfoView provideMsgInfoView(){
        return msgInfoView;
    }
}
