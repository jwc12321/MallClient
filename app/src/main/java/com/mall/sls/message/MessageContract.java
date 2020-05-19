package com.mall.sls.message;

import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.data.entity.MessageInfo;
import com.mall.sls.data.entity.MessageTypeInfo;

import java.util.List;

/**
 * @author jwc on 2020/5/19.
 * 描述：
 */
public interface MessageContract {
    interface MsgTypePresenter extends BasePresenter{
        void getMsgType();
    }

    interface MsgTypeView extends BaseView<MsgTypePresenter>{
        void renderMsgType(List<MessageTypeInfo> messageTypeInfos);
    }

    interface MsgInfoPresenter extends BasePresenter{
        void getMsgInfo(String refreshType,String typeId);
        void getMoreMsgInfo(String typeId);
        void msgEmpty(String msgId);
        void msgChangeStatus(String msgId);
    }

    interface MsgInfoView extends BaseView<MsgInfoPresenter>{
        void renderMsgInfo(MessageInfo messageInfo);
        void renderMoreMsgInfo(MessageInfo messageInfo);
        void renderMsgEmpty();
        void renderMsgChangeStatus();
    }
}
