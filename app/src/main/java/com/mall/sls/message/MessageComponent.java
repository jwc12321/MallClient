package com.mall.sls.message;

/**
 * @author jwc on 2020/5/19.
 * 描述：
 */

import com.mall.sls.ActivityScope;
import com.mall.sls.ApplicationComponent;
import com.mall.sls.message.ui.MessageTypeActivity;
import com.mall.sls.message.ui.OrderMessageActivity;
import com.mall.sls.message.ui.ReminderMessageActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {MessageModule.class})
public interface MessageComponent {
    void inject(MessageTypeActivity messageTypeActivity);
    void inject(OrderMessageActivity orderMessageActivity);
    void inject(ReminderMessageActivity reminderMessageActivity);
}
