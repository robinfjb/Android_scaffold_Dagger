package robin.scaffold.dagger.notification

import robin.scaffold.dagger.R

/**
 * The notification constants are used inside the NotificationHelper
 *
 * @see NotificationHelper
 */
object NotificationConstants {

    /**
     * Notification channel id String.xml path
     */
    const val NOTIFICATION_CHANNEL_ID = R.string.notification_channel_id

    /**
     * Notification small icon String.xml path
     */
    const val NOTIFICATION_SMALL_ICON = R.mipmap.ic_launcher

    /**
     * Notification channel name String.xml path
     */
    const val CHANNEL_NAME = R.string.notification_name

    /**
     * Notification channel description String.xml path
     */
    const val CHANNEL_DESCRIPTION = R.string.notification_description

    /**
     * Risk changed notification title String.xml path
     */
    const val NOTIFICATION_CONTENT_TITLE_RISK_CHANGED = R.string.notification_headline

    /**
     * Risk changed notification content text String.xml path
     */
    const val NOTIFICATION_CONTENT_TEXT_RISK_CHANGED = R.string.notification_body
}
