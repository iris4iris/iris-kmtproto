// Generated from Bot API 10.3 (August 24, 2026). Do not edit.
// Regenerate: ./gradlew :bot:generateBotApi
package iris.kmtproto.bot

fun acceptedGiftTypesFromMap(raw: Any?): AcceptedGiftTypes? {
    val m = raw as? Map<*, *> ?: return null
    return AcceptedGiftTypes(
        unlimitedGifts = botBool(m["unlimited_gifts"]) ?: false,
        limitedGifts = botBool(m["limited_gifts"]) ?: false,
        uniqueGifts = botBool(m["unique_gifts"]) ?: false,
        premiumSubscription = botBool(m["premium_subscription"]) ?: false,
        giftsFromChannels = botBool(m["gifts_from_channels"]) ?: false,
    )
}

fun affiliateInfoFromMap(raw: Any?): AffiliateInfo? {
    val m = raw as? Map<*, *> ?: return null
    return AffiliateInfo(
        affiliateUser = userFromMap(m["affiliate_user"]),
        affiliateChat = chatFromMap(m["affiliate_chat"]),
        commissionPerMille = botLong(m["commission_per_mille"]) ?: 0,
        amount = botLong(m["amount"]) ?: 0,
        nanostarAmount = botLong(m["nanostar_amount"]),
    )
}

fun animationFromMap(raw: Any?): Animation? {
    val m = raw as? Map<*, *> ?: return null
    return Animation(
        fileId = botString(m["file_id"]) ?: "",
        fileUniqueId = botString(m["file_unique_id"]) ?: "",
        width = botLong(m["width"]) ?: 0,
        height = botLong(m["height"]) ?: 0,
        duration = botLong(m["duration"]) ?: 0,
        thumbnail = photoSizeFromMap(m["thumbnail"]),
        fileName = botString(m["file_name"]),
        mimeType = botString(m["mime_type"]),
        fileSize = botLong(m["file_size"]),
    )
}

fun audioFromMap(raw: Any?): Audio? {
    val m = raw as? Map<*, *> ?: return null
    return Audio(
        fileId = botString(m["file_id"]) ?: "",
        fileUniqueId = botString(m["file_unique_id"]) ?: "",
        duration = botLong(m["duration"]) ?: 0,
        performer = botString(m["performer"]),
        title = botString(m["title"]),
        fileName = botString(m["file_name"]),
        mimeType = botString(m["mime_type"]),
        fileSize = botLong(m["file_size"]),
        thumbnail = photoSizeFromMap(m["thumbnail"]),
    )
}

fun backgroundFillFreeformGradientFromMap(raw: Any?): BackgroundFillFreeformGradient? {
    val m = raw as? Map<*, *> ?: return null
    return BackgroundFillFreeformGradient(
        type = botString(m["type"]) ?: "",
        colors = botList(m["colors"]) { botLong(it) } ?: emptyList(),
    )
}

fun backgroundFillGradientFromMap(raw: Any?): BackgroundFillGradient? {
    val m = raw as? Map<*, *> ?: return null
    return BackgroundFillGradient(
        type = botString(m["type"]) ?: "",
        topColor = botLong(m["top_color"]) ?: 0,
        bottomColor = botLong(m["bottom_color"]) ?: 0,
        rotationAngle = botLong(m["rotation_angle"]) ?: 0,
    )
}

fun backgroundFillSolidFromMap(raw: Any?): BackgroundFillSolid? {
    val m = raw as? Map<*, *> ?: return null
    return BackgroundFillSolid(
        type = botString(m["type"]) ?: "",
        color = botLong(m["color"]) ?: 0,
    )
}

fun backgroundTypeChatThemeFromMap(raw: Any?): BackgroundTypeChatTheme? {
    val m = raw as? Map<*, *> ?: return null
    return BackgroundTypeChatTheme(
        type = botString(m["type"]) ?: "",
        themeName = botString(m["theme_name"]) ?: "",
    )
}

fun backgroundTypeFillFromMap(raw: Any?): BackgroundTypeFill? {
    val m = raw as? Map<*, *> ?: return null
    return BackgroundTypeFill(
        type = botString(m["type"]) ?: "",
        fill = backgroundFillFromMap(m["fill"]),
        darkThemeDimming = botLong(m["dark_theme_dimming"]) ?: 0,
    )
}

fun backgroundTypePatternFromMap(raw: Any?): BackgroundTypePattern? {
    val m = raw as? Map<*, *> ?: return null
    return BackgroundTypePattern(
        type = botString(m["type"]) ?: "",
        document = documentFromMap(m["document"]),
        fill = backgroundFillFromMap(m["fill"]),
        intensity = botLong(m["intensity"]) ?: 0,
        isInverted = botBool(m["is_inverted"]),
        isMoving = botBool(m["is_moving"]),
    )
}

fun backgroundTypeWallpaperFromMap(raw: Any?): BackgroundTypeWallpaper? {
    val m = raw as? Map<*, *> ?: return null
    return BackgroundTypeWallpaper(
        type = botString(m["type"]) ?: "",
        document = documentFromMap(m["document"]),
        darkThemeDimming = botLong(m["dark_theme_dimming"]) ?: 0,
        isBlurred = botBool(m["is_blurred"]),
        isMoving = botBool(m["is_moving"]),
    )
}

fun birthdateFromMap(raw: Any?): Birthdate? {
    val m = raw as? Map<*, *> ?: return null
    return Birthdate(
        day = botLong(m["day"]) ?: 0,
        month = botLong(m["month"]) ?: 0,
        year = botLong(m["year"]),
    )
}

fun botAccessSettingsFromMap(raw: Any?): BotAccessSettings? {
    val m = raw as? Map<*, *> ?: return null
    return BotAccessSettings(
        isAccessRestricted = botBool(m["is_access_restricted"]) ?: false,
        addedUsers = botList(m["added_users"]) { userFromMap(it) },
    )
}

fun botCommandFromMap(raw: Any?): BotCommand? {
    val m = raw as? Map<*, *> ?: return null
    return BotCommand(
        command = botString(m["command"]) ?: "",
        description = botString(m["description"]) ?: "",
        isEphemeral = botBool(m["is_ephemeral"]),
    )
}

fun botCommandScopeAllChatAdministratorsFromMap(raw: Any?): BotCommandScopeAllChatAdministrators? {
    val m = raw as? Map<*, *> ?: return null
    return BotCommandScopeAllChatAdministrators(
        type = botString(m["type"]) ?: "",
    )
}

fun botCommandScopeAllGroupChatsFromMap(raw: Any?): BotCommandScopeAllGroupChats? {
    val m = raw as? Map<*, *> ?: return null
    return BotCommandScopeAllGroupChats(
        type = botString(m["type"]) ?: "",
    )
}

fun botCommandScopeAllPrivateChatsFromMap(raw: Any?): BotCommandScopeAllPrivateChats? {
    val m = raw as? Map<*, *> ?: return null
    return BotCommandScopeAllPrivateChats(
        type = botString(m["type"]) ?: "",
    )
}

fun botCommandScopeChatFromMap(raw: Any?): BotCommandScopeChat? {
    val m = raw as? Map<*, *> ?: return null
    return BotCommandScopeChat(
        type = botString(m["type"]) ?: "",
        chatId = LongOrString.of(m["chat_id"]),
    )
}

fun botCommandScopeChatAdministratorsFromMap(raw: Any?): BotCommandScopeChatAdministrators? {
    val m = raw as? Map<*, *> ?: return null
    return BotCommandScopeChatAdministrators(
        type = botString(m["type"]) ?: "",
        chatId = LongOrString.of(m["chat_id"]),
    )
}

fun botCommandScopeChatMemberFromMap(raw: Any?): BotCommandScopeChatMember? {
    val m = raw as? Map<*, *> ?: return null
    return BotCommandScopeChatMember(
        type = botString(m["type"]) ?: "",
        chatId = LongOrString.of(m["chat_id"]),
        userId = botLong(m["user_id"]) ?: 0,
    )
}

fun botCommandScopeDefaultFromMap(raw: Any?): BotCommandScopeDefault? {
    val m = raw as? Map<*, *> ?: return null
    return BotCommandScopeDefault(
        type = botString(m["type"]) ?: "",
    )
}

fun botDescriptionFromMap(raw: Any?): BotDescription? {
    val m = raw as? Map<*, *> ?: return null
    return BotDescription(
        description = botString(m["description"]) ?: "",
    )
}

fun botNameFromMap(raw: Any?): BotName? {
    val m = raw as? Map<*, *> ?: return null
    return BotName(
        name = botString(m["name"]) ?: "",
    )
}

fun botShortDescriptionFromMap(raw: Any?): BotShortDescription? {
    val m = raw as? Map<*, *> ?: return null
    return BotShortDescription(
        shortDescription = botString(m["short_description"]) ?: "",
    )
}

fun botSubscriptionUpdatedFromMap(raw: Any?): BotSubscriptionUpdated? {
    val m = raw as? Map<*, *> ?: return null
    return BotSubscriptionUpdated(
        user = userFromMap(m["user"]),
        invoicePayload = botString(m["invoice_payload"]) ?: "",
        state = botString(m["state"]) ?: "",
    )
}

fun businessBotRightsFromMap(raw: Any?): BusinessBotRights? {
    val m = raw as? Map<*, *> ?: return null
    return BusinessBotRights(
        canReply = botBool(m["can_reply"]),
        canReadMessages = botBool(m["can_read_messages"]),
        canDeleteSentMessages = botBool(m["can_delete_sent_messages"]),
        canDeleteAllMessages = botBool(m["can_delete_all_messages"]),
        canEditName = botBool(m["can_edit_name"]),
        canEditBio = botBool(m["can_edit_bio"]),
        canEditProfilePhoto = botBool(m["can_edit_profile_photo"]),
        canEditUsername = botBool(m["can_edit_username"]),
        canChangeGiftSettings = botBool(m["can_change_gift_settings"]),
        canViewGiftsAndStars = botBool(m["can_view_gifts_and_stars"]),
        canConvertGiftsToStars = botBool(m["can_convert_gifts_to_stars"]),
        canTransferAndUpgradeGifts = botBool(m["can_transfer_and_upgrade_gifts"]),
        canTransferStars = botBool(m["can_transfer_stars"]),
        canManageStories = botBool(m["can_manage_stories"]),
    )
}

fun businessConnectionFromMap(raw: Any?): BusinessConnection? {
    val m = raw as? Map<*, *> ?: return null
    return BusinessConnection(
        id = botString(m["id"]) ?: "",
        user = userFromMap(m["user"]),
        userChatId = botLong(m["user_chat_id"]) ?: 0,
        date = botLong(m["date"]) ?: 0,
        rights = businessBotRightsFromMap(m["rights"]),
        isEnabled = botBool(m["is_enabled"]) ?: false,
    )
}

fun businessIntroFromMap(raw: Any?): BusinessIntro? {
    val m = raw as? Map<*, *> ?: return null
    return BusinessIntro(
        title = botString(m["title"]),
        message = botString(m["message"]),
        sticker = stickerFromMap(m["sticker"]),
    )
}

fun businessLocationFromMap(raw: Any?): BusinessLocation? {
    val m = raw as? Map<*, *> ?: return null
    return BusinessLocation(
        address = botString(m["address"]) ?: "",
        location = locationFromMap(m["location"]),
    )
}

fun businessMessagesDeletedFromMap(raw: Any?): BusinessMessagesDeleted? {
    val m = raw as? Map<*, *> ?: return null
    return BusinessMessagesDeleted(
        businessConnectionId = botString(m["business_connection_id"]) ?: "",
        chat = chatFromMap(m["chat"]),
        messageIds = botList(m["message_ids"]) { botLong(it) } ?: emptyList(),
    )
}

fun businessOpeningHoursFromMap(raw: Any?): BusinessOpeningHours? {
    val m = raw as? Map<*, *> ?: return null
    return BusinessOpeningHours(
        timeZoneName = botString(m["time_zone_name"]) ?: "",
        openingHours = botList(m["opening_hours"]) { businessOpeningHoursIntervalFromMap(it) } ?: emptyList(),
    )
}

fun businessOpeningHoursIntervalFromMap(raw: Any?): BusinessOpeningHoursInterval? {
    val m = raw as? Map<*, *> ?: return null
    return BusinessOpeningHoursInterval(
        openingMinute = botLong(m["opening_minute"]) ?: 0,
        closingMinute = botLong(m["closing_minute"]) ?: 0,
    )
}

fun callbackGameFromMap(raw: Any?): CallbackGame? =
    if (raw is Map<*, *> || raw == true) CallbackGame else null

fun callbackQueryFromMap(raw: Any?): CallbackQuery? {
    val m = raw as? Map<*, *> ?: return null
    return CallbackQuery(
        id = botString(m["id"]) ?: "",
        from = userFromMap(m["from"]),
        message = maybeInaccessibleMessageFromMap(m["message"]),
        inlineMessageId = botString(m["inline_message_id"]),
        chatInstance = botString(m["chat_instance"]) ?: "",
        data = botString(m["data"]),
        gameShortName = botString(m["game_short_name"]),
    )
}

fun chatFromMap(raw: Any?): Chat? {
    val m = raw as? Map<*, *> ?: return null
    return Chat(
        id = botLong(m["id"]) ?: 0,
        type = botString(m["type"]) ?: "",
        title = botString(m["title"]),
        username = botString(m["username"]),
        firstName = botString(m["first_name"]),
        lastName = botString(m["last_name"]),
        isForum = botBool(m["is_forum"]),
        isDirectMessages = botBool(m["is_direct_messages"]),
    )
}

fun chatAdministratorRightsFromMap(raw: Any?): ChatAdministratorRights? {
    val m = raw as? Map<*, *> ?: return null
    return ChatAdministratorRights(
        isAnonymous = botBool(m["is_anonymous"]) ?: false,
        canManageChat = botBool(m["can_manage_chat"]) ?: false,
        canDeleteMessages = botBool(m["can_delete_messages"]) ?: false,
        canManageVideoChats = botBool(m["can_manage_video_chats"]) ?: false,
        canRestrictMembers = botBool(m["can_restrict_members"]) ?: false,
        canPromoteMembers = botBool(m["can_promote_members"]) ?: false,
        canChangeInfo = botBool(m["can_change_info"]) ?: false,
        canInviteUsers = botBool(m["can_invite_users"]) ?: false,
        canPostStories = botBool(m["can_post_stories"]) ?: false,
        canEditStories = botBool(m["can_edit_stories"]) ?: false,
        canDeleteStories = botBool(m["can_delete_stories"]) ?: false,
        canPostMessages = botBool(m["can_post_messages"]),
        canEditMessages = botBool(m["can_edit_messages"]),
        canPinMessages = botBool(m["can_pin_messages"]),
        canManageTopics = botBool(m["can_manage_topics"]),
        canManageDirectMessages = botBool(m["can_manage_direct_messages"]),
        canManageTags = botBool(m["can_manage_tags"]),
        canSendWelcomeMessages = botBool(m["can_send_welcome_messages"]) ?: false,
    )
}

fun chatBackgroundFromMap(raw: Any?): ChatBackground? {
    val m = raw as? Map<*, *> ?: return null
    return ChatBackground(
        type = backgroundTypeFromMap(m["type"]),
    )
}

fun chatBoostFromMap(raw: Any?): ChatBoost? {
    val m = raw as? Map<*, *> ?: return null
    return ChatBoost(
        boostId = botString(m["boost_id"]) ?: "",
        addDate = botLong(m["add_date"]) ?: 0,
        expirationDate = botLong(m["expiration_date"]) ?: 0,
        source = chatBoostSourceFromMap(m["source"]),
    )
}

fun chatBoostAddedFromMap(raw: Any?): ChatBoostAdded? {
    val m = raw as? Map<*, *> ?: return null
    return ChatBoostAdded(
        boostCount = botLong(m["boost_count"]) ?: 0,
    )
}

fun chatBoostRemovedFromMap(raw: Any?): ChatBoostRemoved? {
    val m = raw as? Map<*, *> ?: return null
    return ChatBoostRemoved(
        chat = chatFromMap(m["chat"]),
        boostId = botString(m["boost_id"]) ?: "",
        removeDate = botLong(m["remove_date"]) ?: 0,
        source = chatBoostSourceFromMap(m["source"]),
    )
}

fun chatBoostSourceGiftCodeFromMap(raw: Any?): ChatBoostSourceGiftCode? {
    val m = raw as? Map<*, *> ?: return null
    return ChatBoostSourceGiftCode(
        source = botString(m["source"]) ?: "",
        user = userFromMap(m["user"]),
    )
}

fun chatBoostSourceGiveawayFromMap(raw: Any?): ChatBoostSourceGiveaway? {
    val m = raw as? Map<*, *> ?: return null
    return ChatBoostSourceGiveaway(
        source = botString(m["source"]) ?: "",
        giveawayMessageId = botLong(m["giveaway_message_id"]) ?: 0,
        user = userFromMap(m["user"]),
        prizeStarCount = botLong(m["prize_star_count"]),
        isUnclaimed = botBool(m["is_unclaimed"]),
    )
}

fun chatBoostSourcePremiumFromMap(raw: Any?): ChatBoostSourcePremium? {
    val m = raw as? Map<*, *> ?: return null
    return ChatBoostSourcePremium(
        source = botString(m["source"]) ?: "",
        user = userFromMap(m["user"]),
    )
}

fun chatBoostUpdatedFromMap(raw: Any?): ChatBoostUpdated? {
    val m = raw as? Map<*, *> ?: return null
    return ChatBoostUpdated(
        chat = chatFromMap(m["chat"]),
        boost = chatBoostFromMap(m["boost"]),
    )
}

fun chatFullInfoFromMap(raw: Any?): ChatFullInfo? {
    val m = raw as? Map<*, *> ?: return null
    return ChatFullInfo(
        id = botLong(m["id"]) ?: 0,
        type = botString(m["type"]) ?: "",
        title = botString(m["title"]),
        username = botString(m["username"]),
        firstName = botString(m["first_name"]),
        lastName = botString(m["last_name"]),
        isForum = botBool(m["is_forum"]),
        isDirectMessages = botBool(m["is_direct_messages"]),
        accentColorId = botLong(m["accent_color_id"]) ?: 0,
        maxReactionCount = botLong(m["max_reaction_count"]) ?: 0,
        photo = chatPhotoFromMap(m["photo"]),
        activeUsernames = botList(m["active_usernames"]) { botString(it) },
        birthdate = birthdateFromMap(m["birthdate"]),
        businessIntro = businessIntroFromMap(m["business_intro"]),
        businessLocation = businessLocationFromMap(m["business_location"]),
        businessOpeningHours = businessOpeningHoursFromMap(m["business_opening_hours"]),
        personalChat = chatFromMap(m["personal_chat"]),
        parentChat = chatFromMap(m["parent_chat"]),
        availableReactions = botList(m["available_reactions"]) { reactionTypeFromMap(it) },
        backgroundCustomEmojiId = botString(m["background_custom_emoji_id"]),
        profileAccentColorId = botLong(m["profile_accent_color_id"]),
        profileBackgroundCustomEmojiId = botString(m["profile_background_custom_emoji_id"]),
        emojiStatusCustomEmojiId = botString(m["emoji_status_custom_emoji_id"]),
        emojiStatusExpirationDate = botLong(m["emoji_status_expiration_date"]),
        bio = botString(m["bio"]),
        hasPrivateForwards = botBool(m["has_private_forwards"]),
        hasRestrictedVoiceAndVideoMessages = botBool(m["has_restricted_voice_and_video_messages"]),
        joinToSendMessages = botBool(m["join_to_send_messages"]),
        joinByRequest = botBool(m["join_by_request"]),
        description = botString(m["description"]),
        inviteLink = botString(m["invite_link"]),
        pinnedMessage = messageFromMap(m["pinned_message"]),
        permissions = chatPermissionsFromMap(m["permissions"]),
        acceptedGiftTypes = acceptedGiftTypesFromMap(m["accepted_gift_types"]),
        canSendPaidMedia = botBool(m["can_send_paid_media"]),
        slowModeDelay = botLong(m["slow_mode_delay"]),
        unrestrictBoostCount = botLong(m["unrestrict_boost_count"]),
        messageAutoDeleteTime = botLong(m["message_auto_delete_time"]),
        hasAggressiveAntiSpamEnabled = botBool(m["has_aggressive_anti_spam_enabled"]),
        hasHiddenMembers = botBool(m["has_hidden_members"]),
        hasProtectedContent = botBool(m["has_protected_content"]),
        hasVisibleHistory = botBool(m["has_visible_history"]),
        stickerSetName = botString(m["sticker_set_name"]),
        canSetStickerSet = botBool(m["can_set_sticker_set"]),
        customEmojiStickerSetName = botString(m["custom_emoji_sticker_set_name"]),
        linkedChatId = botLong(m["linked_chat_id"]),
        location = chatLocationFromMap(m["location"]),
        rating = userRatingFromMap(m["rating"]),
        firstProfileAudio = audioFromMap(m["first_profile_audio"]),
        uniqueGiftColors = uniqueGiftColorsFromMap(m["unique_gift_colors"]),
        paidMessageStarCount = botLong(m["paid_message_star_count"]),
        guardBot = userFromMap(m["guard_bot"]),
        community = communityFromMap(m["community"]),
    )
}

fun chatInviteLinkFromMap(raw: Any?): ChatInviteLink? {
    val m = raw as? Map<*, *> ?: return null
    return ChatInviteLink(
        inviteLink = botString(m["invite_link"]) ?: "",
        creator = userFromMap(m["creator"]),
        createsJoinRequest = botBool(m["creates_join_request"]) ?: false,
        isPrimary = botBool(m["is_primary"]) ?: false,
        isRevoked = botBool(m["is_revoked"]) ?: false,
        name = botString(m["name"]),
        expireDate = botLong(m["expire_date"]),
        memberLimit = botLong(m["member_limit"]),
        pendingJoinRequestCount = botLong(m["pending_join_request_count"]),
        subscriptionPeriod = botLong(m["subscription_period"]),
        subscriptionPrice = botLong(m["subscription_price"]),
    )
}

fun chatJoinRequestFromMap(raw: Any?): ChatJoinRequest? {
    val m = raw as? Map<*, *> ?: return null
    return ChatJoinRequest(
        chat = chatFromMap(m["chat"]),
        from = userFromMap(m["from"]),
        userChatId = botLong(m["user_chat_id"]) ?: 0,
        date = botLong(m["date"]) ?: 0,
        bio = botString(m["bio"]),
        inviteLink = chatInviteLinkFromMap(m["invite_link"]),
        queryId = botString(m["query_id"]),
    )
}

fun chatLocationFromMap(raw: Any?): ChatLocation? {
    val m = raw as? Map<*, *> ?: return null
    return ChatLocation(
        location = locationFromMap(m["location"]),
        address = botString(m["address"]) ?: "",
    )
}

fun chatMemberAdministratorFromMap(raw: Any?): ChatMemberAdministrator? {
    val m = raw as? Map<*, *> ?: return null
    return ChatMemberAdministrator(
        status = botString(m["status"]) ?: "",
        user = userFromMap(m["user"]),
        canBeEdited = botBool(m["can_be_edited"]) ?: false,
        isAnonymous = botBool(m["is_anonymous"]) ?: false,
        canManageChat = botBool(m["can_manage_chat"]) ?: false,
        canDeleteMessages = botBool(m["can_delete_messages"]) ?: false,
        canManageVideoChats = botBool(m["can_manage_video_chats"]) ?: false,
        canRestrictMembers = botBool(m["can_restrict_members"]) ?: false,
        canPromoteMembers = botBool(m["can_promote_members"]) ?: false,
        canChangeInfo = botBool(m["can_change_info"]) ?: false,
        canInviteUsers = botBool(m["can_invite_users"]) ?: false,
        canPostStories = botBool(m["can_post_stories"]) ?: false,
        canEditStories = botBool(m["can_edit_stories"]) ?: false,
        canDeleteStories = botBool(m["can_delete_stories"]) ?: false,
        canPostMessages = botBool(m["can_post_messages"]),
        canEditMessages = botBool(m["can_edit_messages"]),
        canPinMessages = botBool(m["can_pin_messages"]),
        canManageTopics = botBool(m["can_manage_topics"]),
        canManageDirectMessages = botBool(m["can_manage_direct_messages"]),
        canManageTags = botBool(m["can_manage_tags"]),
        canSendWelcomeMessages = botBool(m["can_send_welcome_messages"]) ?: false,
        customTitle = botString(m["custom_title"]),
    )
}

fun chatMemberBannedFromMap(raw: Any?): ChatMemberBanned? {
    val m = raw as? Map<*, *> ?: return null
    return ChatMemberBanned(
        status = botString(m["status"]) ?: "",
        user = userFromMap(m["user"]),
        untilDate = botLong(m["until_date"]) ?: 0,
    )
}

fun chatMemberLeftFromMap(raw: Any?): ChatMemberLeft? {
    val m = raw as? Map<*, *> ?: return null
    return ChatMemberLeft(
        status = botString(m["status"]) ?: "",
        user = userFromMap(m["user"]),
    )
}

fun chatMemberMemberFromMap(raw: Any?): ChatMemberMember? {
    val m = raw as? Map<*, *> ?: return null
    return ChatMemberMember(
        status = botString(m["status"]) ?: "",
        tag = botString(m["tag"]),
        user = userFromMap(m["user"]),
        untilDate = botLong(m["until_date"]),
    )
}

fun chatMemberOwnerFromMap(raw: Any?): ChatMemberOwner? {
    val m = raw as? Map<*, *> ?: return null
    return ChatMemberOwner(
        status = botString(m["status"]) ?: "",
        user = userFromMap(m["user"]),
        isAnonymous = botBool(m["is_anonymous"]) ?: false,
        customTitle = botString(m["custom_title"]),
    )
}

fun chatMemberRestrictedFromMap(raw: Any?): ChatMemberRestricted? {
    val m = raw as? Map<*, *> ?: return null
    return ChatMemberRestricted(
        status = botString(m["status"]) ?: "",
        tag = botString(m["tag"]),
        user = userFromMap(m["user"]),
        isMember = botBool(m["is_member"]) ?: false,
        canSendMessages = botBool(m["can_send_messages"]) ?: false,
        canSendAudios = botBool(m["can_send_audios"]) ?: false,
        canSendDocuments = botBool(m["can_send_documents"]) ?: false,
        canSendPhotos = botBool(m["can_send_photos"]) ?: false,
        canSendVideos = botBool(m["can_send_videos"]) ?: false,
        canSendVideoNotes = botBool(m["can_send_video_notes"]) ?: false,
        canSendVoiceNotes = botBool(m["can_send_voice_notes"]) ?: false,
        canSendPolls = botBool(m["can_send_polls"]) ?: false,
        canSendOtherMessages = botBool(m["can_send_other_messages"]) ?: false,
        canAddWebPagePreviews = botBool(m["can_add_web_page_previews"]) ?: false,
        canReactToMessages = botBool(m["can_react_to_messages"]) ?: false,
        canEditTag = botBool(m["can_edit_tag"]) ?: false,
        canChangeInfo = botBool(m["can_change_info"]) ?: false,
        canInviteUsers = botBool(m["can_invite_users"]) ?: false,
        canPinMessages = botBool(m["can_pin_messages"]) ?: false,
        canManageTopics = botBool(m["can_manage_topics"]) ?: false,
        untilDate = botLong(m["until_date"]) ?: 0,
    )
}

fun chatMemberUpdatedFromMap(raw: Any?): ChatMemberUpdated? {
    val m = raw as? Map<*, *> ?: return null
    return ChatMemberUpdated(
        chat = chatFromMap(m["chat"]),
        from = userFromMap(m["from"]),
        date = botLong(m["date"]) ?: 0,
        oldChatMember = chatMemberFromMap(m["old_chat_member"]),
        newChatMember = chatMemberFromMap(m["new_chat_member"]),
        inviteLink = chatInviteLinkFromMap(m["invite_link"]),
        viaJoinRequest = botBool(m["via_join_request"]),
        viaChatFolderInviteLink = botBool(m["via_chat_folder_invite_link"]),
    )
}

fun chatOwnerChangedFromMap(raw: Any?): ChatOwnerChanged? {
    val m = raw as? Map<*, *> ?: return null
    return ChatOwnerChanged(
        newOwner = userFromMap(m["new_owner"]),
    )
}

fun chatOwnerLeftFromMap(raw: Any?): ChatOwnerLeft? {
    val m = raw as? Map<*, *> ?: return null
    return ChatOwnerLeft(
        newOwner = userFromMap(m["new_owner"]),
    )
}

fun chatPermissionsFromMap(raw: Any?): ChatPermissions? {
    val m = raw as? Map<*, *> ?: return null
    return ChatPermissions(
        canSendMessages = botBool(m["can_send_messages"]),
        canSendAudios = botBool(m["can_send_audios"]),
        canSendDocuments = botBool(m["can_send_documents"]),
        canSendPhotos = botBool(m["can_send_photos"]),
        canSendVideos = botBool(m["can_send_videos"]),
        canSendVideoNotes = botBool(m["can_send_video_notes"]),
        canSendVoiceNotes = botBool(m["can_send_voice_notes"]),
        canSendPolls = botBool(m["can_send_polls"]),
        canSendOtherMessages = botBool(m["can_send_other_messages"]),
        canAddWebPagePreviews = botBool(m["can_add_web_page_previews"]),
        canReactToMessages = botBool(m["can_react_to_messages"]),
        canEditTag = botBool(m["can_edit_tag"]),
        canChangeInfo = botBool(m["can_change_info"]),
        canInviteUsers = botBool(m["can_invite_users"]),
        canPinMessages = botBool(m["can_pin_messages"]),
        canManageTopics = botBool(m["can_manage_topics"]),
    )
}

fun chatPhotoFromMap(raw: Any?): ChatPhoto? {
    val m = raw as? Map<*, *> ?: return null
    return ChatPhoto(
        smallFileId = botString(m["small_file_id"]) ?: "",
        smallFileUniqueId = botString(m["small_file_unique_id"]) ?: "",
        bigFileId = botString(m["big_file_id"]) ?: "",
        bigFileUniqueId = botString(m["big_file_unique_id"]) ?: "",
    )
}

fun chatSharedFromMap(raw: Any?): ChatShared? {
    val m = raw as? Map<*, *> ?: return null
    return ChatShared(
        requestId = botLong(m["request_id"]) ?: 0,
        chatId = botLong(m["chat_id"]) ?: 0,
        title = botString(m["title"]),
        username = botString(m["username"]),
        photo = botList(m["photo"]) { photoSizeFromMap(it) },
    )
}

fun checklistFromMap(raw: Any?): Checklist? {
    val m = raw as? Map<*, *> ?: return null
    return Checklist(
        title = botString(m["title"]) ?: "",
        titleEntities = botList(m["title_entities"]) { messageEntityFromMap(it) },
        tasks = botList(m["tasks"]) { checklistTaskFromMap(it) } ?: emptyList(),
        othersCanAddTasks = botBool(m["others_can_add_tasks"]),
        othersCanMarkTasksAsDone = botBool(m["others_can_mark_tasks_as_done"]),
    )
}

fun checklistTaskFromMap(raw: Any?): ChecklistTask? {
    val m = raw as? Map<*, *> ?: return null
    return ChecklistTask(
        id = botLong(m["id"]) ?: 0,
        text = botString(m["text"]) ?: "",
        textEntities = botList(m["text_entities"]) { messageEntityFromMap(it) },
        completedByUser = userFromMap(m["completed_by_user"]),
        completedByChat = chatFromMap(m["completed_by_chat"]),
        completionDate = botLong(m["completion_date"]),
    )
}

fun checklistTasksAddedFromMap(raw: Any?): ChecklistTasksAdded? {
    val m = raw as? Map<*, *> ?: return null
    return ChecklistTasksAdded(
        checklistMessage = messageFromMap(m["checklist_message"]),
        tasks = botList(m["tasks"]) { checklistTaskFromMap(it) } ?: emptyList(),
    )
}

fun checklistTasksDoneFromMap(raw: Any?): ChecklistTasksDone? {
    val m = raw as? Map<*, *> ?: return null
    return ChecklistTasksDone(
        checklistMessage = messageFromMap(m["checklist_message"]),
        markedAsDoneTaskIds = botList(m["marked_as_done_task_ids"]) { botLong(it) },
        markedAsNotDoneTaskIds = botList(m["marked_as_not_done_task_ids"]) { botLong(it) },
    )
}

fun chosenInlineResultFromMap(raw: Any?): ChosenInlineResult? {
    val m = raw as? Map<*, *> ?: return null
    return ChosenInlineResult(
        resultId = botString(m["result_id"]) ?: "",
        from = userFromMap(m["from"]),
        location = locationFromMap(m["location"]),
        inlineMessageId = botString(m["inline_message_id"]),
        query = botString(m["query"]) ?: "",
    )
}

fun communityFromMap(raw: Any?): Community? {
    val m = raw as? Map<*, *> ?: return null
    return Community(
        id = botLong(m["id"]) ?: 0,
        name = botString(m["name"]) ?: "",
    )
}

fun communityChatAddedFromMap(raw: Any?): CommunityChatAdded? {
    val m = raw as? Map<*, *> ?: return null
    return CommunityChatAdded(
        community = communityFromMap(m["community"]),
    )
}

fun communityChatJoinedFromMap(raw: Any?): CommunityChatJoined? {
    val m = raw as? Map<*, *> ?: return null
    return CommunityChatJoined(
        community = communityFromMap(m["community"]),
    )
}

fun communityChatRemovedFromMap(raw: Any?): CommunityChatRemoved? =
    if (raw is Map<*, *> || raw == true) CommunityChatRemoved else null

fun contactFromMap(raw: Any?): Contact? {
    val m = raw as? Map<*, *> ?: return null
    return Contact(
        phoneNumber = botString(m["phone_number"]) ?: "",
        firstName = botString(m["first_name"]) ?: "",
        lastName = botString(m["last_name"]),
        userId = botLong(m["user_id"]),
        vcard = botString(m["vcard"]),
    )
}

fun copyTextButtonFromMap(raw: Any?): CopyTextButton? {
    val m = raw as? Map<*, *> ?: return null
    return CopyTextButton(
        text = botString(m["text"]) ?: "",
    )
}

fun diceFromMap(raw: Any?): Dice? {
    val m = raw as? Map<*, *> ?: return null
    return Dice(
        emoji = botString(m["emoji"]) ?: "",
        value = botLong(m["value"]) ?: 0,
    )
}

fun directMessagePriceChangedFromMap(raw: Any?): DirectMessagePriceChanged? {
    val m = raw as? Map<*, *> ?: return null
    return DirectMessagePriceChanged(
        areDirectMessagesEnabled = botBool(m["are_direct_messages_enabled"]) ?: false,
        directMessageStarCount = botLong(m["direct_message_star_count"]),
    )
}

fun directMessagesTopicFromMap(raw: Any?): DirectMessagesTopic? {
    val m = raw as? Map<*, *> ?: return null
    return DirectMessagesTopic(
        topicId = botLong(m["topic_id"]) ?: 0,
        user = userFromMap(m["user"]),
    )
}

fun disabledButtonFromMap(raw: Any?): DisabledButton? =
    if (raw is Map<*, *> || raw == true) DisabledButton else null

fun documentFromMap(raw: Any?): Document? {
    val m = raw as? Map<*, *> ?: return null
    return Document(
        fileId = botString(m["file_id"]) ?: "",
        fileUniqueId = botString(m["file_unique_id"]) ?: "",
        thumbnail = photoSizeFromMap(m["thumbnail"]),
        fileName = botString(m["file_name"]),
        mimeType = botString(m["mime_type"]),
        fileSize = botLong(m["file_size"]),
    )
}

fun encryptedCredentialsFromMap(raw: Any?): EncryptedCredentials? {
    val m = raw as? Map<*, *> ?: return null
    return EncryptedCredentials(
        data = botString(m["data"]) ?: "",
        hash = botString(m["hash"]) ?: "",
        secret = botString(m["secret"]) ?: "",
    )
}

fun encryptedPassportElementFromMap(raw: Any?): EncryptedPassportElement? {
    val m = raw as? Map<*, *> ?: return null
    return EncryptedPassportElement(
        type = botString(m["type"]) ?: "",
        data = botString(m["data"]),
        phoneNumber = botString(m["phone_number"]),
        email = botString(m["email"]),
        files = botList(m["files"]) { passportFileFromMap(it) },
        frontSide = passportFileFromMap(m["front_side"]),
        reverseSide = passportFileFromMap(m["reverse_side"]),
        selfie = passportFileFromMap(m["selfie"]),
        translation = botList(m["translation"]) { passportFileFromMap(it) },
        hash = botString(m["hash"]) ?: "",
    )
}

fun ephemeralMessageParametersFromMap(raw: Any?): EphemeralMessageParameters? {
    val m = raw as? Map<*, *> ?: return null
    return EphemeralMessageParameters(
        receiverUserId = botLong(m["receiver_user_id"]) ?: 0,
        callbackQueryId = botString(m["callback_query_id"]),
        replaceCallbackQueryMessage = botBool(m["replace_callback_query_message"]),
    )
}

fun externalReplyInfoFromMap(raw: Any?): ExternalReplyInfo? {
    val m = raw as? Map<*, *> ?: return null
    return ExternalReplyInfo(
        origin = messageOriginFromMap(m["origin"]),
        chat = chatFromMap(m["chat"]),
        messageId = botLong(m["message_id"]),
        linkPreviewOptions = linkPreviewOptionsFromMap(m["link_preview_options"]),
        animation = animationFromMap(m["animation"]),
        audio = audioFromMap(m["audio"]),
        document = documentFromMap(m["document"]),
        livePhoto = livePhotoFromMap(m["live_photo"]),
        paidMedia = paidMediaInfoFromMap(m["paid_media"]),
        photo = botList(m["photo"]) { photoSizeFromMap(it) },
        sticker = stickerFromMap(m["sticker"]),
        story = storyFromMap(m["story"]),
        video = videoFromMap(m["video"]),
        videoNote = videoNoteFromMap(m["video_note"]),
        voice = voiceFromMap(m["voice"]),
        hasMediaSpoiler = botBool(m["has_media_spoiler"]),
        checklist = checklistFromMap(m["checklist"]),
        contact = contactFromMap(m["contact"]),
        dice = diceFromMap(m["dice"]),
        game = gameFromMap(m["game"]),
        giveaway = giveawayFromMap(m["giveaway"]),
        giveawayWinners = giveawayWinnersFromMap(m["giveaway_winners"]),
        invoice = invoiceFromMap(m["invoice"]),
        location = locationFromMap(m["location"]),
        poll = pollFromMap(m["poll"]),
        venue = venueFromMap(m["venue"]),
    )
}

fun fileFromMap(raw: Any?): File? {
    val m = raw as? Map<*, *> ?: return null
    return File(
        fileId = botString(m["file_id"]) ?: "",
        fileUniqueId = botString(m["file_unique_id"]) ?: "",
        fileSize = botLong(m["file_size"]),
        filePath = botString(m["file_path"]),
    )
}

fun forceReplyFromMap(raw: Any?): ForceReply? {
    val m = raw as? Map<*, *> ?: return null
    return ForceReply(
        forceReply = botBool(m["force_reply"]) ?: false,
        inputFieldPlaceholder = botString(m["input_field_placeholder"]),
        selective = botBool(m["selective"]),
    )
}

fun forumTopicFromMap(raw: Any?): ForumTopic? {
    val m = raw as? Map<*, *> ?: return null
    return ForumTopic(
        messageThreadId = botLong(m["message_thread_id"]) ?: 0,
        name = botString(m["name"]) ?: "",
        iconColor = botLong(m["icon_color"]) ?: 0,
        iconCustomEmojiId = botString(m["icon_custom_emoji_id"]),
        isNameImplicit = botBool(m["is_name_implicit"]),
    )
}

fun forumTopicClosedFromMap(raw: Any?): ForumTopicClosed? =
    if (raw is Map<*, *> || raw == true) ForumTopicClosed else null

fun forumTopicCreatedFromMap(raw: Any?): ForumTopicCreated? {
    val m = raw as? Map<*, *> ?: return null
    return ForumTopicCreated(
        name = botString(m["name"]) ?: "",
        iconColor = botLong(m["icon_color"]) ?: 0,
        iconCustomEmojiId = botString(m["icon_custom_emoji_id"]),
        isNameImplicit = botBool(m["is_name_implicit"]),
    )
}

fun forumTopicEditedFromMap(raw: Any?): ForumTopicEdited? {
    val m = raw as? Map<*, *> ?: return null
    return ForumTopicEdited(
        name = botString(m["name"]),
        iconCustomEmojiId = botString(m["icon_custom_emoji_id"]),
    )
}

fun forumTopicReopenedFromMap(raw: Any?): ForumTopicReopened? =
    if (raw is Map<*, *> || raw == true) ForumTopicReopened else null

fun gameFromMap(raw: Any?): Game? {
    val m = raw as? Map<*, *> ?: return null
    return Game(
        title = botString(m["title"]) ?: "",
        description = botString(m["description"]) ?: "",
        photo = botList(m["photo"]) { photoSizeFromMap(it) } ?: emptyList(),
        text = botString(m["text"]),
        textEntities = botList(m["text_entities"]) { messageEntityFromMap(it) },
        animation = animationFromMap(m["animation"]),
    )
}

fun gameHighScoreFromMap(raw: Any?): GameHighScore? {
    val m = raw as? Map<*, *> ?: return null
    return GameHighScore(
        position = botLong(m["position"]) ?: 0,
        user = userFromMap(m["user"]),
        score = botLong(m["score"]) ?: 0,
    )
}

fun generalForumTopicHiddenFromMap(raw: Any?): GeneralForumTopicHidden? =
    if (raw is Map<*, *> || raw == true) GeneralForumTopicHidden else null

fun generalForumTopicUnhiddenFromMap(raw: Any?): GeneralForumTopicUnhidden? =
    if (raw is Map<*, *> || raw == true) GeneralForumTopicUnhidden else null

fun giftFromMap(raw: Any?): Gift? {
    val m = raw as? Map<*, *> ?: return null
    return Gift(
        id = botString(m["id"]) ?: "",
        sticker = stickerFromMap(m["sticker"]),
        starCount = botLong(m["star_count"]) ?: 0,
        upgradeStarCount = botLong(m["upgrade_star_count"]),
        isPremium = botBool(m["is_premium"]),
        hasColors = botBool(m["has_colors"]),
        totalCount = botLong(m["total_count"]),
        remainingCount = botLong(m["remaining_count"]),
        personalTotalCount = botLong(m["personal_total_count"]),
        personalRemainingCount = botLong(m["personal_remaining_count"]),
        background = giftBackgroundFromMap(m["background"]),
        uniqueGiftVariantCount = botLong(m["unique_gift_variant_count"]),
        publisherChat = chatFromMap(m["publisher_chat"]),
    )
}

fun giftBackgroundFromMap(raw: Any?): GiftBackground? {
    val m = raw as? Map<*, *> ?: return null
    return GiftBackground(
        centerColor = botLong(m["center_color"]) ?: 0,
        edgeColor = botLong(m["edge_color"]) ?: 0,
        textColor = botLong(m["text_color"]) ?: 0,
    )
}

fun giftInfoFromMap(raw: Any?): GiftInfo? {
    val m = raw as? Map<*, *> ?: return null
    return GiftInfo(
        gift = giftFromMap(m["gift"]),
        ownedGiftId = botString(m["owned_gift_id"]),
        convertStarCount = botLong(m["convert_star_count"]),
        prepaidUpgradeStarCount = botLong(m["prepaid_upgrade_star_count"]),
        isUpgradeSeparate = botBool(m["is_upgrade_separate"]),
        canBeUpgraded = botBool(m["can_be_upgraded"]),
        text = botString(m["text"]),
        entities = botList(m["entities"]) { messageEntityFromMap(it) },
        isPrivate = botBool(m["is_private"]),
        uniqueGiftNumber = botLong(m["unique_gift_number"]),
    )
}

fun giftsFromMap(raw: Any?): Gifts? {
    val m = raw as? Map<*, *> ?: return null
    return Gifts(
        gifts = botList(m["gifts"]) { giftFromMap(it) } ?: emptyList(),
    )
}

fun giveawayFromMap(raw: Any?): Giveaway? {
    val m = raw as? Map<*, *> ?: return null
    return Giveaway(
        chats = botList(m["chats"]) { chatFromMap(it) } ?: emptyList(),
        winnersSelectionDate = botLong(m["winners_selection_date"]) ?: 0,
        winnerCount = botLong(m["winner_count"]) ?: 0,
        onlyNewMembers = botBool(m["only_new_members"]),
        hasPublicWinners = botBool(m["has_public_winners"]),
        prizeDescription = botString(m["prize_description"]),
        countryCodes = botList(m["country_codes"]) { botString(it) },
        prizeStarCount = botLong(m["prize_star_count"]),
        premiumSubscriptionMonthCount = botLong(m["premium_subscription_month_count"]),
    )
}

fun giveawayCompletedFromMap(raw: Any?): GiveawayCompleted? {
    val m = raw as? Map<*, *> ?: return null
    return GiveawayCompleted(
        winnerCount = botLong(m["winner_count"]) ?: 0,
        unclaimedPrizeCount = botLong(m["unclaimed_prize_count"]),
        giveawayMessage = messageFromMap(m["giveaway_message"]),
        isStarGiveaway = botBool(m["is_star_giveaway"]),
    )
}

fun giveawayCreatedFromMap(raw: Any?): GiveawayCreated? {
    val m = raw as? Map<*, *> ?: return null
    return GiveawayCreated(
        prizeStarCount = botLong(m["prize_star_count"]),
    )
}

fun giveawayWinnersFromMap(raw: Any?): GiveawayWinners? {
    val m = raw as? Map<*, *> ?: return null
    return GiveawayWinners(
        chat = chatFromMap(m["chat"]),
        giveawayMessageId = botLong(m["giveaway_message_id"]) ?: 0,
        winnersSelectionDate = botLong(m["winners_selection_date"]) ?: 0,
        winnerCount = botLong(m["winner_count"]) ?: 0,
        winners = botList(m["winners"]) { userFromMap(it) } ?: emptyList(),
        additionalChatCount = botLong(m["additional_chat_count"]),
        prizeStarCount = botLong(m["prize_star_count"]),
        premiumSubscriptionMonthCount = botLong(m["premium_subscription_month_count"]),
        unclaimedPrizeCount = botLong(m["unclaimed_prize_count"]),
        onlyNewMembers = botBool(m["only_new_members"]),
        wasRefunded = botBool(m["was_refunded"]),
        prizeDescription = botString(m["prize_description"]),
    )
}

fun inaccessibleMessageFromMap(raw: Any?): InaccessibleMessage? {
    val m = raw as? Map<*, *> ?: return null
    return InaccessibleMessage(
        chat = chatFromMap(m["chat"]),
        messageId = botLong(m["message_id"]) ?: 0,
        date = botLong(m["date"]) ?: 0,
    )
}

fun inlineKeyboardButtonFromMap(raw: Any?): InlineKeyboardButton? {
    val m = raw as? Map<*, *> ?: return null
    return InlineKeyboardButton(
        text = botString(m["text"]) ?: "",
        iconCustomEmojiId = botString(m["icon_custom_emoji_id"]),
        style = botString(m["style"]),
        url = botString(m["url"]),
        callbackData = botString(m["callback_data"]),
        webApp = webAppInfoFromMap(m["web_app"]),
        loginUrl = loginUrlFromMap(m["login_url"]),
        switchInlineQuery = botString(m["switch_inline_query"]),
        switchInlineQueryCurrentChat = botString(m["switch_inline_query_current_chat"]),
        switchInlineQueryChosenChat = switchInlineQueryChosenChatFromMap(m["switch_inline_query_chosen_chat"]),
        copyText = copyTextButtonFromMap(m["copy_text"]),
        callbackGame = callbackGameFromMap(m["callback_game"]),
        pay = botBool(m["pay"]),
        disabled = disabledButtonFromMap(m["disabled"]),
    )
}

fun inlineKeyboardMarkupFromMap(raw: Any?): InlineKeyboardMarkup? {
    val m = raw as? Map<*, *> ?: return null
    return InlineKeyboardMarkup(
        inlineKeyboard = botList(m["inline_keyboard"]) { botList(it) { inlineKeyboardButtonFromMap(it) } } ?: emptyList(),
        forceReply = botBool(m["force_reply"]),
    )
}

fun inlineQueryFromMap(raw: Any?): InlineQuery? {
    val m = raw as? Map<*, *> ?: return null
    return InlineQuery(
        id = botString(m["id"]) ?: "",
        from = userFromMap(m["from"]),
        query = botString(m["query"]) ?: "",
        offset = botString(m["offset"]) ?: "",
        chatType = botString(m["chat_type"]),
        location = locationFromMap(m["location"]),
    )
}

fun inlineQueryResultArticleFromMap(raw: Any?): InlineQueryResultArticle? {
    val m = raw as? Map<*, *> ?: return null
    return InlineQueryResultArticle(
        type = botString(m["type"]) ?: "",
        id = botString(m["id"]) ?: "",
        title = botString(m["title"]) ?: "",
        inputMessageContent = inputMessageContentFromMap(m["input_message_content"]),
        replyMarkup = inlineKeyboardMarkupFromMap(m["reply_markup"]),
        url = botString(m["url"]),
        description = botString(m["description"]),
        thumbnailUrl = botString(m["thumbnail_url"]),
        thumbnailWidth = botLong(m["thumbnail_width"]),
        thumbnailHeight = botLong(m["thumbnail_height"]),
    )
}

fun inlineQueryResultAudioFromMap(raw: Any?): InlineQueryResultAudio? {
    val m = raw as? Map<*, *> ?: return null
    return InlineQueryResultAudio(
        type = botString(m["type"]) ?: "",
        id = botString(m["id"]) ?: "",
        audioUrl = botString(m["audio_url"]) ?: "",
        title = botString(m["title"]) ?: "",
        caption = botString(m["caption"]),
        parseMode = botString(m["parse_mode"]),
        captionEntities = botList(m["caption_entities"]) { messageEntityFromMap(it) },
        performer = botString(m["performer"]),
        audioDuration = botLong(m["audio_duration"]),
        replyMarkup = inlineKeyboardMarkupFromMap(m["reply_markup"]),
        inputMessageContent = inputMessageContentFromMap(m["input_message_content"]),
    )
}

fun inlineQueryResultCachedAudioFromMap(raw: Any?): InlineQueryResultCachedAudio? {
    val m = raw as? Map<*, *> ?: return null
    return InlineQueryResultCachedAudio(
        type = botString(m["type"]) ?: "",
        id = botString(m["id"]) ?: "",
        audioFileId = botString(m["audio_file_id"]) ?: "",
        caption = botString(m["caption"]),
        parseMode = botString(m["parse_mode"]),
        captionEntities = botList(m["caption_entities"]) { messageEntityFromMap(it) },
        replyMarkup = inlineKeyboardMarkupFromMap(m["reply_markup"]),
        inputMessageContent = inputMessageContentFromMap(m["input_message_content"]),
    )
}

fun inlineQueryResultCachedDocumentFromMap(raw: Any?): InlineQueryResultCachedDocument? {
    val m = raw as? Map<*, *> ?: return null
    return InlineQueryResultCachedDocument(
        type = botString(m["type"]) ?: "",
        id = botString(m["id"]) ?: "",
        title = botString(m["title"]) ?: "",
        documentFileId = botString(m["document_file_id"]) ?: "",
        description = botString(m["description"]),
        caption = botString(m["caption"]),
        parseMode = botString(m["parse_mode"]),
        captionEntities = botList(m["caption_entities"]) { messageEntityFromMap(it) },
        replyMarkup = inlineKeyboardMarkupFromMap(m["reply_markup"]),
        inputMessageContent = inputMessageContentFromMap(m["input_message_content"]),
    )
}

fun inlineQueryResultCachedGifFromMap(raw: Any?): InlineQueryResultCachedGif? {
    val m = raw as? Map<*, *> ?: return null
    return InlineQueryResultCachedGif(
        type = botString(m["type"]) ?: "",
        id = botString(m["id"]) ?: "",
        gifFileId = botString(m["gif_file_id"]) ?: "",
        title = botString(m["title"]),
        caption = botString(m["caption"]),
        parseMode = botString(m["parse_mode"]),
        captionEntities = botList(m["caption_entities"]) { messageEntityFromMap(it) },
        showCaptionAboveMedia = botBool(m["show_caption_above_media"]),
        replyMarkup = inlineKeyboardMarkupFromMap(m["reply_markup"]),
        inputMessageContent = inputMessageContentFromMap(m["input_message_content"]),
    )
}

fun inlineQueryResultCachedMpeg4GifFromMap(raw: Any?): InlineQueryResultCachedMpeg4Gif? {
    val m = raw as? Map<*, *> ?: return null
    return InlineQueryResultCachedMpeg4Gif(
        type = botString(m["type"]) ?: "",
        id = botString(m["id"]) ?: "",
        mpeg4FileId = botString(m["mpeg4_file_id"]) ?: "",
        title = botString(m["title"]),
        caption = botString(m["caption"]),
        parseMode = botString(m["parse_mode"]),
        captionEntities = botList(m["caption_entities"]) { messageEntityFromMap(it) },
        showCaptionAboveMedia = botBool(m["show_caption_above_media"]),
        replyMarkup = inlineKeyboardMarkupFromMap(m["reply_markup"]),
        inputMessageContent = inputMessageContentFromMap(m["input_message_content"]),
    )
}

fun inlineQueryResultCachedPhotoFromMap(raw: Any?): InlineQueryResultCachedPhoto? {
    val m = raw as? Map<*, *> ?: return null
    return InlineQueryResultCachedPhoto(
        type = botString(m["type"]) ?: "",
        id = botString(m["id"]) ?: "",
        photoFileId = botString(m["photo_file_id"]) ?: "",
        title = botString(m["title"]),
        description = botString(m["description"]),
        caption = botString(m["caption"]),
        parseMode = botString(m["parse_mode"]),
        captionEntities = botList(m["caption_entities"]) { messageEntityFromMap(it) },
        showCaptionAboveMedia = botBool(m["show_caption_above_media"]),
        replyMarkup = inlineKeyboardMarkupFromMap(m["reply_markup"]),
        inputMessageContent = inputMessageContentFromMap(m["input_message_content"]),
    )
}

fun inlineQueryResultCachedStickerFromMap(raw: Any?): InlineQueryResultCachedSticker? {
    val m = raw as? Map<*, *> ?: return null
    return InlineQueryResultCachedSticker(
        type = botString(m["type"]) ?: "",
        id = botString(m["id"]) ?: "",
        stickerFileId = botString(m["sticker_file_id"]) ?: "",
        replyMarkup = inlineKeyboardMarkupFromMap(m["reply_markup"]),
        inputMessageContent = inputMessageContentFromMap(m["input_message_content"]),
    )
}

fun inlineQueryResultCachedVideoFromMap(raw: Any?): InlineQueryResultCachedVideo? {
    val m = raw as? Map<*, *> ?: return null
    return InlineQueryResultCachedVideo(
        type = botString(m["type"]) ?: "",
        id = botString(m["id"]) ?: "",
        videoFileId = botString(m["video_file_id"]) ?: "",
        title = botString(m["title"]) ?: "",
        description = botString(m["description"]),
        caption = botString(m["caption"]),
        parseMode = botString(m["parse_mode"]),
        captionEntities = botList(m["caption_entities"]) { messageEntityFromMap(it) },
        showCaptionAboveMedia = botBool(m["show_caption_above_media"]),
        replyMarkup = inlineKeyboardMarkupFromMap(m["reply_markup"]),
        inputMessageContent = inputMessageContentFromMap(m["input_message_content"]),
    )
}

fun inlineQueryResultCachedVoiceFromMap(raw: Any?): InlineQueryResultCachedVoice? {
    val m = raw as? Map<*, *> ?: return null
    return InlineQueryResultCachedVoice(
        type = botString(m["type"]) ?: "",
        id = botString(m["id"]) ?: "",
        voiceFileId = botString(m["voice_file_id"]) ?: "",
        title = botString(m["title"]) ?: "",
        caption = botString(m["caption"]),
        parseMode = botString(m["parse_mode"]),
        captionEntities = botList(m["caption_entities"]) { messageEntityFromMap(it) },
        replyMarkup = inlineKeyboardMarkupFromMap(m["reply_markup"]),
        inputMessageContent = inputMessageContentFromMap(m["input_message_content"]),
    )
}

fun inlineQueryResultContactFromMap(raw: Any?): InlineQueryResultContact? {
    val m = raw as? Map<*, *> ?: return null
    return InlineQueryResultContact(
        type = botString(m["type"]) ?: "",
        id = botString(m["id"]) ?: "",
        phoneNumber = botString(m["phone_number"]) ?: "",
        firstName = botString(m["first_name"]) ?: "",
        lastName = botString(m["last_name"]),
        vcard = botString(m["vcard"]),
        replyMarkup = inlineKeyboardMarkupFromMap(m["reply_markup"]),
        inputMessageContent = inputMessageContentFromMap(m["input_message_content"]),
        thumbnailUrl = botString(m["thumbnail_url"]),
        thumbnailWidth = botLong(m["thumbnail_width"]),
        thumbnailHeight = botLong(m["thumbnail_height"]),
    )
}

fun inlineQueryResultDocumentFromMap(raw: Any?): InlineQueryResultDocument? {
    val m = raw as? Map<*, *> ?: return null
    return InlineQueryResultDocument(
        type = botString(m["type"]) ?: "",
        id = botString(m["id"]) ?: "",
        title = botString(m["title"]) ?: "",
        caption = botString(m["caption"]),
        parseMode = botString(m["parse_mode"]),
        captionEntities = botList(m["caption_entities"]) { messageEntityFromMap(it) },
        documentUrl = botString(m["document_url"]) ?: "",
        mimeType = botString(m["mime_type"]) ?: "",
        description = botString(m["description"]),
        replyMarkup = inlineKeyboardMarkupFromMap(m["reply_markup"]),
        inputMessageContent = inputMessageContentFromMap(m["input_message_content"]),
        thumbnailUrl = botString(m["thumbnail_url"]),
        thumbnailWidth = botLong(m["thumbnail_width"]),
        thumbnailHeight = botLong(m["thumbnail_height"]),
    )
}

fun inlineQueryResultGameFromMap(raw: Any?): InlineQueryResultGame? {
    val m = raw as? Map<*, *> ?: return null
    return InlineQueryResultGame(
        type = botString(m["type"]) ?: "",
        id = botString(m["id"]) ?: "",
        gameShortName = botString(m["game_short_name"]) ?: "",
        replyMarkup = inlineKeyboardMarkupFromMap(m["reply_markup"]),
    )
}

fun inlineQueryResultGifFromMap(raw: Any?): InlineQueryResultGif? {
    val m = raw as? Map<*, *> ?: return null
    return InlineQueryResultGif(
        type = botString(m["type"]) ?: "",
        id = botString(m["id"]) ?: "",
        gifUrl = botString(m["gif_url"]) ?: "",
        gifWidth = botLong(m["gif_width"]),
        gifHeight = botLong(m["gif_height"]),
        gifDuration = botLong(m["gif_duration"]),
        thumbnailUrl = botString(m["thumbnail_url"]) ?: "",
        thumbnailMimeType = botString(m["thumbnail_mime_type"]),
        title = botString(m["title"]),
        caption = botString(m["caption"]),
        parseMode = botString(m["parse_mode"]),
        captionEntities = botList(m["caption_entities"]) { messageEntityFromMap(it) },
        showCaptionAboveMedia = botBool(m["show_caption_above_media"]),
        replyMarkup = inlineKeyboardMarkupFromMap(m["reply_markup"]),
        inputMessageContent = inputMessageContentFromMap(m["input_message_content"]),
    )
}

fun inlineQueryResultLocationFromMap(raw: Any?): InlineQueryResultLocation? {
    val m = raw as? Map<*, *> ?: return null
    return InlineQueryResultLocation(
        type = botString(m["type"]) ?: "",
        id = botString(m["id"]) ?: "",
        latitude = botDouble(m["latitude"]) ?: 0.0,
        longitude = botDouble(m["longitude"]) ?: 0.0,
        title = botString(m["title"]) ?: "",
        horizontalAccuracy = botDouble(m["horizontal_accuracy"]),
        livePeriod = botLong(m["live_period"]),
        heading = botLong(m["heading"]),
        proximityAlertRadius = botLong(m["proximity_alert_radius"]),
        replyMarkup = inlineKeyboardMarkupFromMap(m["reply_markup"]),
        inputMessageContent = inputMessageContentFromMap(m["input_message_content"]),
        thumbnailUrl = botString(m["thumbnail_url"]),
        thumbnailWidth = botLong(m["thumbnail_width"]),
        thumbnailHeight = botLong(m["thumbnail_height"]),
    )
}

fun inlineQueryResultMpeg4GifFromMap(raw: Any?): InlineQueryResultMpeg4Gif? {
    val m = raw as? Map<*, *> ?: return null
    return InlineQueryResultMpeg4Gif(
        type = botString(m["type"]) ?: "",
        id = botString(m["id"]) ?: "",
        mpeg4Url = botString(m["mpeg4_url"]) ?: "",
        mpeg4Width = botLong(m["mpeg4_width"]),
        mpeg4Height = botLong(m["mpeg4_height"]),
        mpeg4Duration = botLong(m["mpeg4_duration"]),
        thumbnailUrl = botString(m["thumbnail_url"]) ?: "",
        thumbnailMimeType = botString(m["thumbnail_mime_type"]),
        title = botString(m["title"]),
        caption = botString(m["caption"]),
        parseMode = botString(m["parse_mode"]),
        captionEntities = botList(m["caption_entities"]) { messageEntityFromMap(it) },
        showCaptionAboveMedia = botBool(m["show_caption_above_media"]),
        replyMarkup = inlineKeyboardMarkupFromMap(m["reply_markup"]),
        inputMessageContent = inputMessageContentFromMap(m["input_message_content"]),
    )
}

fun inlineQueryResultPhotoFromMap(raw: Any?): InlineQueryResultPhoto? {
    val m = raw as? Map<*, *> ?: return null
    return InlineQueryResultPhoto(
        type = botString(m["type"]) ?: "",
        id = botString(m["id"]) ?: "",
        photoUrl = botString(m["photo_url"]) ?: "",
        thumbnailUrl = botString(m["thumbnail_url"]) ?: "",
        photoWidth = botLong(m["photo_width"]),
        photoHeight = botLong(m["photo_height"]),
        title = botString(m["title"]),
        description = botString(m["description"]),
        caption = botString(m["caption"]),
        parseMode = botString(m["parse_mode"]),
        captionEntities = botList(m["caption_entities"]) { messageEntityFromMap(it) },
        showCaptionAboveMedia = botBool(m["show_caption_above_media"]),
        replyMarkup = inlineKeyboardMarkupFromMap(m["reply_markup"]),
        inputMessageContent = inputMessageContentFromMap(m["input_message_content"]),
    )
}

fun inlineQueryResultVenueFromMap(raw: Any?): InlineQueryResultVenue? {
    val m = raw as? Map<*, *> ?: return null
    return InlineQueryResultVenue(
        type = botString(m["type"]) ?: "",
        id = botString(m["id"]) ?: "",
        latitude = botDouble(m["latitude"]) ?: 0.0,
        longitude = botDouble(m["longitude"]) ?: 0.0,
        title = botString(m["title"]) ?: "",
        address = botString(m["address"]) ?: "",
        foursquareId = botString(m["foursquare_id"]),
        foursquareType = botString(m["foursquare_type"]),
        googlePlaceId = botString(m["google_place_id"]),
        googlePlaceType = botString(m["google_place_type"]),
        replyMarkup = inlineKeyboardMarkupFromMap(m["reply_markup"]),
        inputMessageContent = inputMessageContentFromMap(m["input_message_content"]),
        thumbnailUrl = botString(m["thumbnail_url"]),
        thumbnailWidth = botLong(m["thumbnail_width"]),
        thumbnailHeight = botLong(m["thumbnail_height"]),
    )
}

fun inlineQueryResultVideoFromMap(raw: Any?): InlineQueryResultVideo? {
    val m = raw as? Map<*, *> ?: return null
    return InlineQueryResultVideo(
        type = botString(m["type"]) ?: "",
        id = botString(m["id"]) ?: "",
        videoUrl = botString(m["video_url"]) ?: "",
        mimeType = botString(m["mime_type"]) ?: "",
        thumbnailUrl = botString(m["thumbnail_url"]) ?: "",
        title = botString(m["title"]) ?: "",
        caption = botString(m["caption"]),
        parseMode = botString(m["parse_mode"]),
        captionEntities = botList(m["caption_entities"]) { messageEntityFromMap(it) },
        showCaptionAboveMedia = botBool(m["show_caption_above_media"]),
        videoWidth = botLong(m["video_width"]),
        videoHeight = botLong(m["video_height"]),
        videoDuration = botLong(m["video_duration"]),
        description = botString(m["description"]),
        replyMarkup = inlineKeyboardMarkupFromMap(m["reply_markup"]),
        inputMessageContent = inputMessageContentFromMap(m["input_message_content"]),
    )
}

fun inlineQueryResultVoiceFromMap(raw: Any?): InlineQueryResultVoice? {
    val m = raw as? Map<*, *> ?: return null
    return InlineQueryResultVoice(
        type = botString(m["type"]) ?: "",
        id = botString(m["id"]) ?: "",
        voiceUrl = botString(m["voice_url"]) ?: "",
        title = botString(m["title"]) ?: "",
        caption = botString(m["caption"]),
        parseMode = botString(m["parse_mode"]),
        captionEntities = botList(m["caption_entities"]) { messageEntityFromMap(it) },
        voiceDuration = botLong(m["voice_duration"]),
        replyMarkup = inlineKeyboardMarkupFromMap(m["reply_markup"]),
        inputMessageContent = inputMessageContentFromMap(m["input_message_content"]),
    )
}

fun inlineQueryResultsButtonFromMap(raw: Any?): InlineQueryResultsButton? {
    val m = raw as? Map<*, *> ?: return null
    return InlineQueryResultsButton(
        text = botString(m["text"]) ?: "",
        webApp = webAppInfoFromMap(m["web_app"]),
        startParameter = botString(m["start_parameter"]),
    )
}

fun inputChecklistFromMap(raw: Any?): InputChecklist? {
    val m = raw as? Map<*, *> ?: return null
    return InputChecklist(
        title = botString(m["title"]) ?: "",
        parseMode = botString(m["parse_mode"]),
        titleEntities = botList(m["title_entities"]) { messageEntityFromMap(it) },
        tasks = botList(m["tasks"]) { inputChecklistTaskFromMap(it) } ?: emptyList(),
        othersCanAddTasks = botBool(m["others_can_add_tasks"]),
        othersCanMarkTasksAsDone = botBool(m["others_can_mark_tasks_as_done"]),
    )
}

fun inputChecklistTaskFromMap(raw: Any?): InputChecklistTask? {
    val m = raw as? Map<*, *> ?: return null
    return InputChecklistTask(
        id = botLong(m["id"]) ?: 0,
        text = botString(m["text"]) ?: "",
        parseMode = botString(m["parse_mode"]),
        textEntities = botList(m["text_entities"]) { messageEntityFromMap(it) },
    )
}

fun inputContactMessageContentFromMap(raw: Any?): InputContactMessageContent? {
    val m = raw as? Map<*, *> ?: return null
    return InputContactMessageContent(
        phoneNumber = botString(m["phone_number"]) ?: "",
        firstName = botString(m["first_name"]) ?: "",
        lastName = botString(m["last_name"]),
        vcard = botString(m["vcard"]),
    )
}

fun inputFileFromMap(raw: Any?): InputFile? = when (raw) {
    is String -> InputFile(raw)
    is Number -> InputFile(raw.toString())
    else -> null
}

fun inputInvoiceMessageContentFromMap(raw: Any?): InputInvoiceMessageContent? {
    val m = raw as? Map<*, *> ?: return null
    return InputInvoiceMessageContent(
        title = botString(m["title"]) ?: "",
        description = botString(m["description"]) ?: "",
        payload = botString(m["payload"]) ?: "",
        providerToken = botString(m["provider_token"]),
        currency = botString(m["currency"]) ?: "",
        prices = botList(m["prices"]) { labeledPriceFromMap(it) } ?: emptyList(),
        maxTipAmount = botLong(m["max_tip_amount"]),
        suggestedTipAmounts = botList(m["suggested_tip_amounts"]) { botLong(it) },
        providerData = botString(m["provider_data"]),
        photoUrl = botString(m["photo_url"]),
        photoSize = botLong(m["photo_size"]),
        photoWidth = botLong(m["photo_width"]),
        photoHeight = botLong(m["photo_height"]),
        needName = botBool(m["need_name"]),
        needPhoneNumber = botBool(m["need_phone_number"]),
        needEmail = botBool(m["need_email"]),
        needShippingAddress = botBool(m["need_shipping_address"]),
        sendPhoneNumberToProvider = botBool(m["send_phone_number_to_provider"]),
        sendEmailToProvider = botBool(m["send_email_to_provider"]),
        isFlexible = botBool(m["is_flexible"]),
    )
}

fun inputLocationMessageContentFromMap(raw: Any?): InputLocationMessageContent? {
    val m = raw as? Map<*, *> ?: return null
    return InputLocationMessageContent(
        latitude = botDouble(m["latitude"]) ?: 0.0,
        longitude = botDouble(m["longitude"]) ?: 0.0,
        horizontalAccuracy = botDouble(m["horizontal_accuracy"]),
        livePeriod = botLong(m["live_period"]),
        heading = botLong(m["heading"]),
        proximityAlertRadius = botLong(m["proximity_alert_radius"]),
    )
}

fun inputMediaAnimationFromMap(raw: Any?): InputMediaAnimation? {
    val m = raw as? Map<*, *> ?: return null
    return InputMediaAnimation(
        type = botString(m["type"]) ?: "",
        media = botString(m["media"]) ?: "",
        thumbnail = botString(m["thumbnail"]),
        caption = botString(m["caption"]),
        parseMode = botString(m["parse_mode"]),
        captionEntities = botList(m["caption_entities"]) { messageEntityFromMap(it) },
        showCaptionAboveMedia = botBool(m["show_caption_above_media"]),
        width = botLong(m["width"]),
        height = botLong(m["height"]),
        duration = botLong(m["duration"]),
        hasSpoiler = botBool(m["has_spoiler"]),
    )
}

fun inputMediaAudioFromMap(raw: Any?): InputMediaAudio? {
    val m = raw as? Map<*, *> ?: return null
    return InputMediaAudio(
        type = botString(m["type"]) ?: "",
        media = botString(m["media"]) ?: "",
        thumbnail = botString(m["thumbnail"]),
        caption = botString(m["caption"]),
        parseMode = botString(m["parse_mode"]),
        captionEntities = botList(m["caption_entities"]) { messageEntityFromMap(it) },
        duration = botLong(m["duration"]),
        performer = botString(m["performer"]),
        title = botString(m["title"]),
    )
}

fun inputMediaDocumentFromMap(raw: Any?): InputMediaDocument? {
    val m = raw as? Map<*, *> ?: return null
    return InputMediaDocument(
        type = botString(m["type"]) ?: "",
        media = botString(m["media"]) ?: "",
        thumbnail = botString(m["thumbnail"]),
        caption = botString(m["caption"]),
        parseMode = botString(m["parse_mode"]),
        captionEntities = botList(m["caption_entities"]) { messageEntityFromMap(it) },
        disableContentTypeDetection = botBool(m["disable_content_type_detection"]),
    )
}

fun inputMediaLinkFromMap(raw: Any?): InputMediaLink? {
    val m = raw as? Map<*, *> ?: return null
    return InputMediaLink(
        type = botString(m["type"]) ?: "",
        url = botString(m["url"]) ?: "",
    )
}

fun inputMediaLivePhotoFromMap(raw: Any?): InputMediaLivePhoto? {
    val m = raw as? Map<*, *> ?: return null
    return InputMediaLivePhoto(
        type = botString(m["type"]) ?: "",
        media = botString(m["media"]) ?: "",
        photo = botString(m["photo"]) ?: "",
        caption = botString(m["caption"]),
        parseMode = botString(m["parse_mode"]),
        captionEntities = botList(m["caption_entities"]) { messageEntityFromMap(it) },
        showCaptionAboveMedia = botBool(m["show_caption_above_media"]),
        hasSpoiler = botBool(m["has_spoiler"]),
    )
}

fun inputMediaLocationFromMap(raw: Any?): InputMediaLocation? {
    val m = raw as? Map<*, *> ?: return null
    return InputMediaLocation(
        type = botString(m["type"]) ?: "",
        latitude = botDouble(m["latitude"]) ?: 0.0,
        longitude = botDouble(m["longitude"]) ?: 0.0,
        horizontalAccuracy = botDouble(m["horizontal_accuracy"]),
    )
}

fun inputMediaPhotoFromMap(raw: Any?): InputMediaPhoto? {
    val m = raw as? Map<*, *> ?: return null
    return InputMediaPhoto(
        type = botString(m["type"]) ?: "",
        media = botString(m["media"]) ?: "",
        caption = botString(m["caption"]),
        parseMode = botString(m["parse_mode"]),
        captionEntities = botList(m["caption_entities"]) { messageEntityFromMap(it) },
        showCaptionAboveMedia = botBool(m["show_caption_above_media"]),
        hasSpoiler = botBool(m["has_spoiler"]),
    )
}

fun inputMediaStickerFromMap(raw: Any?): InputMediaSticker? {
    val m = raw as? Map<*, *> ?: return null
    return InputMediaSticker(
        type = botString(m["type"]) ?: "",
        media = botString(m["media"]) ?: "",
        emoji = botString(m["emoji"]),
    )
}

fun inputMediaVenueFromMap(raw: Any?): InputMediaVenue? {
    val m = raw as? Map<*, *> ?: return null
    return InputMediaVenue(
        type = botString(m["type"]) ?: "",
        latitude = botDouble(m["latitude"]) ?: 0.0,
        longitude = botDouble(m["longitude"]) ?: 0.0,
        title = botString(m["title"]) ?: "",
        address = botString(m["address"]) ?: "",
        foursquareId = botString(m["foursquare_id"]),
        foursquareType = botString(m["foursquare_type"]),
        googlePlaceId = botString(m["google_place_id"]),
        googlePlaceType = botString(m["google_place_type"]),
    )
}

fun inputMediaVideoFromMap(raw: Any?): InputMediaVideo? {
    val m = raw as? Map<*, *> ?: return null
    return InputMediaVideo(
        type = botString(m["type"]) ?: "",
        media = botString(m["media"]) ?: "",
        thumbnail = botString(m["thumbnail"]),
        cover = botString(m["cover"]),
        startTimestamp = botLong(m["start_timestamp"]),
        caption = botString(m["caption"]),
        parseMode = botString(m["parse_mode"]),
        captionEntities = botList(m["caption_entities"]) { messageEntityFromMap(it) },
        showCaptionAboveMedia = botBool(m["show_caption_above_media"]),
        width = botLong(m["width"]),
        height = botLong(m["height"]),
        duration = botLong(m["duration"]),
        supportsStreaming = botBool(m["supports_streaming"]),
        hasSpoiler = botBool(m["has_spoiler"]),
    )
}

fun inputMediaVoiceNoteFromMap(raw: Any?): InputMediaVoiceNote? {
    val m = raw as? Map<*, *> ?: return null
    return InputMediaVoiceNote(
        type = botString(m["type"]) ?: "",
        media = botString(m["media"]) ?: "",
        caption = botString(m["caption"]),
        parseMode = botString(m["parse_mode"]),
        captionEntities = botList(m["caption_entities"]) { messageEntityFromMap(it) },
        duration = botLong(m["duration"]),
    )
}

fun inputPaidMediaLivePhotoFromMap(raw: Any?): InputPaidMediaLivePhoto? {
    val m = raw as? Map<*, *> ?: return null
    return InputPaidMediaLivePhoto(
        type = botString(m["type"]) ?: "",
        media = botString(m["media"]) ?: "",
        photo = botString(m["photo"]) ?: "",
    )
}

fun inputPaidMediaPhotoFromMap(raw: Any?): InputPaidMediaPhoto? {
    val m = raw as? Map<*, *> ?: return null
    return InputPaidMediaPhoto(
        type = botString(m["type"]) ?: "",
        media = botString(m["media"]) ?: "",
    )
}

fun inputPaidMediaVideoFromMap(raw: Any?): InputPaidMediaVideo? {
    val m = raw as? Map<*, *> ?: return null
    return InputPaidMediaVideo(
        type = botString(m["type"]) ?: "",
        media = botString(m["media"]) ?: "",
        thumbnail = botString(m["thumbnail"]),
        cover = botString(m["cover"]),
        startTimestamp = botLong(m["start_timestamp"]),
        width = botLong(m["width"]),
        height = botLong(m["height"]),
        duration = botLong(m["duration"]),
        supportsStreaming = botBool(m["supports_streaming"]),
    )
}

fun inputPollOptionFromMap(raw: Any?): InputPollOption? {
    val m = raw as? Map<*, *> ?: return null
    return InputPollOption(
        text = botString(m["text"]) ?: "",
        textParseMode = botString(m["text_parse_mode"]),
        textEntities = botList(m["text_entities"]) { messageEntityFromMap(it) },
        media = inputPollOptionMediaFromMap(m["media"]),
    )
}

fun inputProfilePhotoAnimatedFromMap(raw: Any?): InputProfilePhotoAnimated? {
    val m = raw as? Map<*, *> ?: return null
    return InputProfilePhotoAnimated(
        type = botString(m["type"]) ?: "",
        animation = botString(m["animation"]) ?: "",
        mainFrameTimestamp = botDouble(m["main_frame_timestamp"]),
    )
}

fun inputProfilePhotoStaticFromMap(raw: Any?): InputProfilePhotoStatic? {
    val m = raw as? Map<*, *> ?: return null
    return InputProfilePhotoStatic(
        type = botString(m["type"]) ?: "",
        photo = botString(m["photo"]) ?: "",
    )
}

fun inputRichBlockAnchorFromMap(raw: Any?): InputRichBlockAnchor? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockAnchor(
        type = botString(m["type"]) ?: "",
        name = botString(m["name"]) ?: "",
    )
}

fun inputRichBlockAnimationFromMap(raw: Any?): InputRichBlockAnimation? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockAnimation(
        type = botString(m["type"]) ?: "",
        animation = inputMediaAnimationFromMap(m["animation"]),
        caption = richBlockCaptionFromMap(m["caption"]),
    )
}

fun inputRichBlockAudioFromMap(raw: Any?): InputRichBlockAudio? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockAudio(
        type = botString(m["type"]) ?: "",
        audio = inputMediaAudioFromMap(m["audio"]),
        caption = richBlockCaptionFromMap(m["caption"]),
    )
}

fun inputRichBlockBlockQuotationFromMap(raw: Any?): InputRichBlockBlockQuotation? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockBlockQuotation(
        type = botString(m["type"]) ?: "",
        blocks = botList(m["blocks"]) { inputRichBlockFromMap(it) } ?: emptyList(),
        credit = richTextFromMap(m["credit"]),
    )
}

fun inputRichBlockButtonsFromMap(raw: Any?): InputRichBlockButtons? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockButtons(
        type = botString(m["type"]) ?: "",
        buttons = botList(m["buttons"]) { richMessageButtonFromMap(it) } ?: emptyList(),
        align = botString(m["align"]),
    )
}

fun inputRichBlockCollageFromMap(raw: Any?): InputRichBlockCollage? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockCollage(
        type = botString(m["type"]) ?: "",
        blocks = botList(m["blocks"]) { inputRichBlockFromMap(it) } ?: emptyList(),
        caption = richBlockCaptionFromMap(m["caption"]),
    )
}

fun inputRichBlockDetailsFromMap(raw: Any?): InputRichBlockDetails? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockDetails(
        type = botString(m["type"]) ?: "",
        summary = richTextFromMap(m["summary"]),
        blocks = botList(m["blocks"]) { inputRichBlockFromMap(it) } ?: emptyList(),
        isOpen = botBool(m["is_open"]),
    )
}

fun inputRichBlockDividerFromMap(raw: Any?): InputRichBlockDivider? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockDivider(
        type = botString(m["type"]) ?: "",
    )
}

fun inputRichBlockDocumentFromMap(raw: Any?): InputRichBlockDocument? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockDocument(
        type = botString(m["type"]) ?: "",
        document = inputMediaDocumentFromMap(m["document"]),
        caption = richBlockCaptionFromMap(m["caption"]),
    )
}

fun inputRichBlockExpandableBlockQuotationFromMap(raw: Any?): InputRichBlockExpandableBlockQuotation? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockExpandableBlockQuotation(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
        credit = richTextFromMap(m["credit"]),
    )
}

fun inputRichBlockFooterFromMap(raw: Any?): InputRichBlockFooter? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockFooter(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
    )
}

fun inputRichBlockListFromMap(raw: Any?): InputRichBlockList? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockList(
        type = botString(m["type"]) ?: "",
        items = botList(m["items"]) { inputRichBlockListItemFromMap(it) } ?: emptyList(),
    )
}

fun inputRichBlockListItemFromMap(raw: Any?): InputRichBlockListItem? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockListItem(
        blocks = botList(m["blocks"]) { inputRichBlockFromMap(it) } ?: emptyList(),
        hasCheckbox = botBool(m["has_checkbox"]),
        isChecked = botBool(m["is_checked"]),
        value = botLong(m["value"]),
        type = botString(m["type"]),
    )
}

fun inputRichBlockMapFromMap(raw: Any?): InputRichBlockMap? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockMap(
        type = botString(m["type"]) ?: "",
        location = locationFromMap(m["location"]),
        zoom = botLong(m["zoom"]),
        width = botLong(m["width"]),
        height = botLong(m["height"]),
        caption = richBlockCaptionFromMap(m["caption"]),
    )
}

fun inputRichBlockMathematicalExpressionFromMap(raw: Any?): InputRichBlockMathematicalExpression? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockMathematicalExpression(
        type = botString(m["type"]) ?: "",
        expression = botString(m["expression"]) ?: "",
    )
}

fun inputRichBlockParagraphFromMap(raw: Any?): InputRichBlockParagraph? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockParagraph(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
    )
}

fun inputRichBlockPhotoFromMap(raw: Any?): InputRichBlockPhoto? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockPhoto(
        type = botString(m["type"]) ?: "",
        photo = inputMediaPhotoFromMap(m["photo"]),
        caption = richBlockCaptionFromMap(m["caption"]),
    )
}

fun inputRichBlockPreformattedFromMap(raw: Any?): InputRichBlockPreformatted? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockPreformatted(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
        language = botString(m["language"]),
    )
}

fun inputRichBlockPullQuotationFromMap(raw: Any?): InputRichBlockPullQuotation? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockPullQuotation(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
        credit = richTextFromMap(m["credit"]),
    )
}

fun inputRichBlockSectionHeadingFromMap(raw: Any?): InputRichBlockSectionHeading? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockSectionHeading(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
        size = botLong(m["size"]) ?: 0,
    )
}

fun inputRichBlockSlideshowFromMap(raw: Any?): InputRichBlockSlideshow? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockSlideshow(
        type = botString(m["type"]) ?: "",
        blocks = botList(m["blocks"]) { inputRichBlockFromMap(it) } ?: emptyList(),
        caption = richBlockCaptionFromMap(m["caption"]),
    )
}

fun inputRichBlockTableFromMap(raw: Any?): InputRichBlockTable? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockTable(
        type = botString(m["type"]) ?: "",
        cells = botList(m["cells"]) { botList(it) { richBlockTableCellFromMap(it) } } ?: emptyList(),
        isBordered = botBool(m["is_bordered"]),
        isStriped = botBool(m["is_striped"]),
        isCompact = botBool(m["is_compact"]),
        caption = richTextFromMap(m["caption"]),
    )
}

fun inputRichBlockThinkingFromMap(raw: Any?): InputRichBlockThinking? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockThinking(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
    )
}

fun inputRichBlockVideoFromMap(raw: Any?): InputRichBlockVideo? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockVideo(
        type = botString(m["type"]) ?: "",
        video = inputMediaVideoFromMap(m["video"]),
        caption = richBlockCaptionFromMap(m["caption"]),
    )
}

fun inputRichBlockVoiceNoteFromMap(raw: Any?): InputRichBlockVoiceNote? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichBlockVoiceNote(
        type = botString(m["type"]) ?: "",
        voiceNote = inputMediaVoiceNoteFromMap(m["voice_note"]),
        caption = richBlockCaptionFromMap(m["caption"]),
    )
}

fun inputRichMessageFromMap(raw: Any?): InputRichMessage? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichMessage(
        blocks = botList(m["blocks"]) { inputRichBlockFromMap(it) },
        html = botString(m["html"]),
        markdown = botString(m["markdown"]),
        media = botList(m["media"]) { inputRichMessageMediaFromMap(it) },
        isRtl = botBool(m["is_rtl"]),
        skipEntityDetection = botBool(m["skip_entity_detection"]),
    )
}

fun inputRichMessageContentFromMap(raw: Any?): InputRichMessageContent? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichMessageContent(
        richMessage = inputRichMessageFromMap(m["rich_message"]),
    )
}

fun inputRichMessageMediaFromMap(raw: Any?): InputRichMessageMedia? {
    val m = raw as? Map<*, *> ?: return null
    return InputRichMessageMedia(
        id = botString(m["id"]) ?: "",
        media = mediaOfInputRichMessageMediaFromMap(m["media"]),
    )
}

fun inputStickerFromMap(raw: Any?): InputSticker? {
    val m = raw as? Map<*, *> ?: return null
    return InputSticker(
        sticker = botString(m["sticker"]) ?: "",
        format = botString(m["format"]) ?: "",
        emojiList = botList(m["emoji_list"]) { botString(it) } ?: emptyList(),
        maskPosition = maskPositionFromMap(m["mask_position"]),
        keywords = botList(m["keywords"]) { botString(it) },
    )
}

fun inputStoryContentPhotoFromMap(raw: Any?): InputStoryContentPhoto? {
    val m = raw as? Map<*, *> ?: return null
    return InputStoryContentPhoto(
        type = botString(m["type"]) ?: "",
        photo = botString(m["photo"]) ?: "",
    )
}

fun inputStoryContentVideoFromMap(raw: Any?): InputStoryContentVideo? {
    val m = raw as? Map<*, *> ?: return null
    return InputStoryContentVideo(
        type = botString(m["type"]) ?: "",
        video = botString(m["video"]) ?: "",
        duration = botDouble(m["duration"]),
        coverFrameTimestamp = botDouble(m["cover_frame_timestamp"]),
        isAnimation = botBool(m["is_animation"]),
    )
}

fun inputTextMessageContentFromMap(raw: Any?): InputTextMessageContent? {
    val m = raw as? Map<*, *> ?: return null
    return InputTextMessageContent(
        messageText = botString(m["message_text"]) ?: "",
        parseMode = botString(m["parse_mode"]),
        entities = botList(m["entities"]) { messageEntityFromMap(it) },
        linkPreviewOptions = linkPreviewOptionsFromMap(m["link_preview_options"]),
    )
}

fun inputVenueMessageContentFromMap(raw: Any?): InputVenueMessageContent? {
    val m = raw as? Map<*, *> ?: return null
    return InputVenueMessageContent(
        latitude = botDouble(m["latitude"]) ?: 0.0,
        longitude = botDouble(m["longitude"]) ?: 0.0,
        title = botString(m["title"]) ?: "",
        address = botString(m["address"]) ?: "",
        foursquareId = botString(m["foursquare_id"]),
        foursquareType = botString(m["foursquare_type"]),
        googlePlaceId = botString(m["google_place_id"]),
        googlePlaceType = botString(m["google_place_type"]),
    )
}

fun invoiceFromMap(raw: Any?): Invoice? {
    val m = raw as? Map<*, *> ?: return null
    return Invoice(
        title = botString(m["title"]) ?: "",
        description = botString(m["description"]) ?: "",
        startParameter = botString(m["start_parameter"]) ?: "",
        currency = botString(m["currency"]) ?: "",
        totalAmount = botLong(m["total_amount"]) ?: 0,
    )
}

fun keyboardButtonFromMap(raw: Any?): KeyboardButton? {
    val m = raw as? Map<*, *> ?: return null
    return KeyboardButton(
        text = botString(m["text"]) ?: "",
        iconCustomEmojiId = botString(m["icon_custom_emoji_id"]),
        style = botString(m["style"]),
        requestUsers = keyboardButtonRequestUsersFromMap(m["request_users"]),
        requestChat = keyboardButtonRequestChatFromMap(m["request_chat"]),
        requestManagedBot = keyboardButtonRequestManagedBotFromMap(m["request_managed_bot"]),
        requestContact = botBool(m["request_contact"]),
        requestLocation = botBool(m["request_location"]),
        requestPoll = keyboardButtonPollTypeFromMap(m["request_poll"]),
        webApp = webAppInfoFromMap(m["web_app"]),
    )
}

fun keyboardButtonPollTypeFromMap(raw: Any?): KeyboardButtonPollType? {
    val m = raw as? Map<*, *> ?: return null
    return KeyboardButtonPollType(
        type = botString(m["type"]),
    )
}

fun keyboardButtonRequestChatFromMap(raw: Any?): KeyboardButtonRequestChat? {
    val m = raw as? Map<*, *> ?: return null
    return KeyboardButtonRequestChat(
        requestId = botLong(m["request_id"]) ?: 0,
        chatIsChannel = botBool(m["chat_is_channel"]) ?: false,
        chatIsForum = botBool(m["chat_is_forum"]),
        chatHasUsername = botBool(m["chat_has_username"]),
        chatIsCreated = botBool(m["chat_is_created"]),
        userAdministratorRights = chatAdministratorRightsFromMap(m["user_administrator_rights"]),
        botAdministratorRights = chatAdministratorRightsFromMap(m["bot_administrator_rights"]),
        botIsMember = botBool(m["bot_is_member"]),
        requestTitle = botBool(m["request_title"]),
        requestUsername = botBool(m["request_username"]),
        requestPhoto = botBool(m["request_photo"]),
    )
}

fun keyboardButtonRequestManagedBotFromMap(raw: Any?): KeyboardButtonRequestManagedBot? {
    val m = raw as? Map<*, *> ?: return null
    return KeyboardButtonRequestManagedBot(
        requestId = botLong(m["request_id"]) ?: 0,
        suggestedName = botString(m["suggested_name"]),
        suggestedUsername = botString(m["suggested_username"]),
    )
}

fun keyboardButtonRequestUsersFromMap(raw: Any?): KeyboardButtonRequestUsers? {
    val m = raw as? Map<*, *> ?: return null
    return KeyboardButtonRequestUsers(
        requestId = botLong(m["request_id"]) ?: 0,
        userIsBot = botBool(m["user_is_bot"]),
        userIsPremium = botBool(m["user_is_premium"]),
        maxQuantity = botLong(m["max_quantity"]),
        requestName = botBool(m["request_name"]),
        requestUsername = botBool(m["request_username"]),
        requestPhoto = botBool(m["request_photo"]),
    )
}

fun labeledPriceFromMap(raw: Any?): LabeledPrice? {
    val m = raw as? Map<*, *> ?: return null
    return LabeledPrice(
        label = botString(m["label"]) ?: "",
        amount = botLong(m["amount"]) ?: 0,
    )
}

fun linkFromMap(raw: Any?): Link? {
    val m = raw as? Map<*, *> ?: return null
    return Link(
        url = botString(m["url"]) ?: "",
    )
}

fun linkPreviewOptionsFromMap(raw: Any?): LinkPreviewOptions? {
    val m = raw as? Map<*, *> ?: return null
    return LinkPreviewOptions(
        isDisabled = botBool(m["is_disabled"]),
        url = botString(m["url"]),
        preferSmallMedia = botBool(m["prefer_small_media"]),
        preferLargeMedia = botBool(m["prefer_large_media"]),
        showAboveText = botBool(m["show_above_text"]),
    )
}

fun livePhotoFromMap(raw: Any?): LivePhoto? {
    val m = raw as? Map<*, *> ?: return null
    return LivePhoto(
        photo = botList(m["photo"]) { photoSizeFromMap(it) },
        fileId = botString(m["file_id"]) ?: "",
        fileUniqueId = botString(m["file_unique_id"]) ?: "",
        width = botLong(m["width"]) ?: 0,
        height = botLong(m["height"]) ?: 0,
        duration = botLong(m["duration"]) ?: 0,
        mimeType = botString(m["mime_type"]),
        fileSize = botLong(m["file_size"]),
    )
}

fun locationFromMap(raw: Any?): Location? {
    val m = raw as? Map<*, *> ?: return null
    return Location(
        latitude = botDouble(m["latitude"]) ?: 0.0,
        longitude = botDouble(m["longitude"]) ?: 0.0,
        horizontalAccuracy = botDouble(m["horizontal_accuracy"]),
        livePeriod = botLong(m["live_period"]),
        heading = botLong(m["heading"]),
        proximityAlertRadius = botLong(m["proximity_alert_radius"]),
    )
}

fun locationAddressFromMap(raw: Any?): LocationAddress? {
    val m = raw as? Map<*, *> ?: return null
    return LocationAddress(
        countryCode = botString(m["country_code"]) ?: "",
        state = botString(m["state"]),
        city = botString(m["city"]),
        street = botString(m["street"]),
    )
}

fun loginUrlFromMap(raw: Any?): LoginUrl? {
    val m = raw as? Map<*, *> ?: return null
    return LoginUrl(
        url = botString(m["url"]) ?: "",
        forwardText = botString(m["forward_text"]),
        botUsername = botString(m["bot_username"]),
        requestWriteAccess = botBool(m["request_write_access"]),
    )
}

fun managedBotCreatedFromMap(raw: Any?): ManagedBotCreated? {
    val m = raw as? Map<*, *> ?: return null
    return ManagedBotCreated(
        bot = userFromMap(m["bot"]),
    )
}

fun managedBotUpdatedFromMap(raw: Any?): ManagedBotUpdated? {
    val m = raw as? Map<*, *> ?: return null
    return ManagedBotUpdated(
        user = userFromMap(m["user"]),
        bot = userFromMap(m["bot"]),
    )
}

fun maskPositionFromMap(raw: Any?): MaskPosition? {
    val m = raw as? Map<*, *> ?: return null
    return MaskPosition(
        point = botString(m["point"]) ?: "",
        xShift = botDouble(m["x_shift"]) ?: 0.0,
        yShift = botDouble(m["y_shift"]) ?: 0.0,
        scale = botDouble(m["scale"]) ?: 0.0,
    )
}

fun menuButtonCommandsFromMap(raw: Any?): MenuButtonCommands? {
    val m = raw as? Map<*, *> ?: return null
    return MenuButtonCommands(
        type = botString(m["type"]) ?: "",
    )
}

fun menuButtonDefaultFromMap(raw: Any?): MenuButtonDefault? {
    val m = raw as? Map<*, *> ?: return null
    return MenuButtonDefault(
        type = botString(m["type"]) ?: "",
    )
}

fun menuButtonWebAppFromMap(raw: Any?): MenuButtonWebApp? {
    val m = raw as? Map<*, *> ?: return null
    return MenuButtonWebApp(
        type = botString(m["type"]) ?: "",
        text = botString(m["text"]) ?: "",
        webApp = webAppInfoFromMap(m["web_app"]),
    )
}

fun messageFromMap(raw: Any?): Message? {
    val m = raw as? Map<*, *> ?: return null
    return Message(
        messageId = botLong(m["message_id"]) ?: 0,
        messageThreadId = botLong(m["message_thread_id"]),
        directMessagesTopic = directMessagesTopicFromMap(m["direct_messages_topic"]),
        from = userFromMap(m["from"]),
        senderChat = chatFromMap(m["sender_chat"]),
        senderBoostCount = botLong(m["sender_boost_count"]),
        senderBusinessBot = userFromMap(m["sender_business_bot"]),
        senderTag = botString(m["sender_tag"]),
        receiverUser = userFromMap(m["receiver_user"]),
        ephemeralMessageId = botLong(m["ephemeral_message_id"]),
        date = botLong(m["date"]) ?: 0,
        guestQueryId = botString(m["guest_query_id"]),
        businessConnectionId = botString(m["business_connection_id"]),
        chat = chatFromMap(m["chat"]),
        forwardOrigin = messageOriginFromMap(m["forward_origin"]),
        isTopicMessage = botBool(m["is_topic_message"]),
        isAutomaticForward = botBool(m["is_automatic_forward"]),
        replyToMessage = messageFromMap(m["reply_to_message"]),
        externalReply = externalReplyInfoFromMap(m["external_reply"]),
        quote = textQuoteFromMap(m["quote"]),
        replyToStory = storyFromMap(m["reply_to_story"]),
        replyToChecklistTaskId = botLong(m["reply_to_checklist_task_id"]),
        replyToPollOptionId = botString(m["reply_to_poll_option_id"]),
        viaBot = userFromMap(m["via_bot"]),
        guestBotCallerUser = userFromMap(m["guest_bot_caller_user"]),
        guestBotCallerChat = chatFromMap(m["guest_bot_caller_chat"]),
        editDate = botLong(m["edit_date"]),
        hasProtectedContent = botBool(m["has_protected_content"]),
        isFromOffline = botBool(m["is_from_offline"]),
        isPaidPost = botBool(m["is_paid_post"]),
        mediaGroupId = botString(m["media_group_id"]),
        authorSignature = botString(m["author_signature"]),
        paidStarCount = botLong(m["paid_star_count"]),
        text = botString(m["text"]),
        entities = botList(m["entities"]) { messageEntityFromMap(it) },
        linkPreviewOptions = linkPreviewOptionsFromMap(m["link_preview_options"]),
        suggestedPostInfo = suggestedPostInfoFromMap(m["suggested_post_info"]),
        effectId = botString(m["effect_id"]),
        richMessage = richMessageFromMap(m["rich_message"]),
        animation = animationFromMap(m["animation"]),
        audio = audioFromMap(m["audio"]),
        document = documentFromMap(m["document"]),
        livePhoto = livePhotoFromMap(m["live_photo"]),
        paidMedia = paidMediaInfoFromMap(m["paid_media"]),
        photo = botList(m["photo"]) { photoSizeFromMap(it) },
        sticker = stickerFromMap(m["sticker"]),
        story = storyFromMap(m["story"]),
        video = videoFromMap(m["video"]),
        videoNote = videoNoteFromMap(m["video_note"]),
        voice = voiceFromMap(m["voice"]),
        caption = botString(m["caption"]),
        captionEntities = botList(m["caption_entities"]) { messageEntityFromMap(it) },
        showCaptionAboveMedia = botBool(m["show_caption_above_media"]),
        hasMediaSpoiler = botBool(m["has_media_spoiler"]),
        checklist = checklistFromMap(m["checklist"]),
        contact = contactFromMap(m["contact"]),
        dice = diceFromMap(m["dice"]),
        game = gameFromMap(m["game"]),
        poll = pollFromMap(m["poll"]),
        venue = venueFromMap(m["venue"]),
        location = locationFromMap(m["location"]),
        newChatMembers = botList(m["new_chat_members"]) { userFromMap(it) },
        leftChatMember = userFromMap(m["left_chat_member"]),
        chatOwnerLeft = chatOwnerLeftFromMap(m["chat_owner_left"]),
        chatOwnerChanged = chatOwnerChangedFromMap(m["chat_owner_changed"]),
        newChatTitle = botString(m["new_chat_title"]),
        newChatPhoto = botList(m["new_chat_photo"]) { photoSizeFromMap(it) },
        deleteChatPhoto = botBool(m["delete_chat_photo"]),
        groupChatCreated = botBool(m["group_chat_created"]),
        supergroupChatCreated = botBool(m["supergroup_chat_created"]),
        channelChatCreated = botBool(m["channel_chat_created"]),
        messageAutoDeleteTimerChanged = messageAutoDeleteTimerChangedFromMap(m["message_auto_delete_timer_changed"]),
        migrateToChatId = botLong(m["migrate_to_chat_id"]),
        migrateFromChatId = botLong(m["migrate_from_chat_id"]),
        pinnedMessage = maybeInaccessibleMessageFromMap(m["pinned_message"]),
        invoice = invoiceFromMap(m["invoice"]),
        successfulPayment = successfulPaymentFromMap(m["successful_payment"]),
        refundedPayment = refundedPaymentFromMap(m["refunded_payment"]),
        usersShared = usersSharedFromMap(m["users_shared"]),
        chatShared = chatSharedFromMap(m["chat_shared"]),
        gift = giftInfoFromMap(m["gift"]),
        uniqueGift = uniqueGiftInfoFromMap(m["unique_gift"]),
        giftUpgradeSent = giftInfoFromMap(m["gift_upgrade_sent"]),
        connectedWebsite = botString(m["connected_website"]),
        writeAccessAllowed = writeAccessAllowedFromMap(m["write_access_allowed"]),
        passportData = passportDataFromMap(m["passport_data"]),
        proximityAlertTriggered = proximityAlertTriggeredFromMap(m["proximity_alert_triggered"]),
        boostAdded = chatBoostAddedFromMap(m["boost_added"]),
        chatBackgroundSet = chatBackgroundFromMap(m["chat_background_set"]),
        checklistTasksDone = checklistTasksDoneFromMap(m["checklist_tasks_done"]),
        checklistTasksAdded = checklistTasksAddedFromMap(m["checklist_tasks_added"]),
        communityChatAdded = communityChatAddedFromMap(m["community_chat_added"]),
        communityChatJoined = communityChatJoinedFromMap(m["community_chat_joined"]),
        communityChatRemoved = communityChatRemovedFromMap(m["community_chat_removed"]),
        directMessagePriceChanged = directMessagePriceChangedFromMap(m["direct_message_price_changed"]),
        forumTopicCreated = forumTopicCreatedFromMap(m["forum_topic_created"]),
        forumTopicEdited = forumTopicEditedFromMap(m["forum_topic_edited"]),
        forumTopicClosed = forumTopicClosedFromMap(m["forum_topic_closed"]),
        forumTopicReopened = forumTopicReopenedFromMap(m["forum_topic_reopened"]),
        generalForumTopicHidden = generalForumTopicHiddenFromMap(m["general_forum_topic_hidden"]),
        generalForumTopicUnhidden = generalForumTopicUnhiddenFromMap(m["general_forum_topic_unhidden"]),
        giveawayCreated = giveawayCreatedFromMap(m["giveaway_created"]),
        giveaway = giveawayFromMap(m["giveaway"]),
        giveawayWinners = giveawayWinnersFromMap(m["giveaway_winners"]),
        giveawayCompleted = giveawayCompletedFromMap(m["giveaway_completed"]),
        managedBotCreated = managedBotCreatedFromMap(m["managed_bot_created"]),
        paidMessagePriceChanged = paidMessagePriceChangedFromMap(m["paid_message_price_changed"]),
        pollOptionAdded = pollOptionAddedFromMap(m["poll_option_added"]),
        pollOptionDeleted = pollOptionDeletedFromMap(m["poll_option_deleted"]),
        suggestedPostApproved = suggestedPostApprovedFromMap(m["suggested_post_approved"]),
        suggestedPostApprovalFailed = suggestedPostApprovalFailedFromMap(m["suggested_post_approval_failed"]),
        suggestedPostDeclined = suggestedPostDeclinedFromMap(m["suggested_post_declined"]),
        suggestedPostPaid = suggestedPostPaidFromMap(m["suggested_post_paid"]),
        suggestedPostRefunded = suggestedPostRefundedFromMap(m["suggested_post_refunded"]),
        videoChatScheduled = videoChatScheduledFromMap(m["video_chat_scheduled"]),
        videoChatStarted = videoChatStartedFromMap(m["video_chat_started"]),
        videoChatEnded = videoChatEndedFromMap(m["video_chat_ended"]),
        videoChatParticipantsInvited = videoChatParticipantsInvitedFromMap(m["video_chat_participants_invited"]),
        webAppData = webAppDataFromMap(m["web_app_data"]),
        replyMarkup = inlineKeyboardMarkupFromMap(m["reply_markup"]),
    )
}

fun messageAutoDeleteTimerChangedFromMap(raw: Any?): MessageAutoDeleteTimerChanged? {
    val m = raw as? Map<*, *> ?: return null
    return MessageAutoDeleteTimerChanged(
        messageAutoDeleteTime = botLong(m["message_auto_delete_time"]) ?: 0,
    )
}

fun messageEntityFromMap(raw: Any?): MessageEntity? {
    val m = raw as? Map<*, *> ?: return null
    return MessageEntity(
        type = botString(m["type"]) ?: "",
        offset = botLong(m["offset"]) ?: 0,
        length = botLong(m["length"]) ?: 0,
        url = botString(m["url"]),
        user = userFromMap(m["user"]),
        language = botString(m["language"]),
        customEmojiId = botString(m["custom_emoji_id"]),
        unixTime = botLong(m["unix_time"]),
        dateTimeFormat = botString(m["date_time_format"]),
    )
}

fun messageGenerationStoppedFromMap(raw: Any?): MessageGenerationStopped? {
    val m = raw as? Map<*, *> ?: return null
    return MessageGenerationStopped(
        chat = chatFromMap(m["chat"]),
        messageThreadId = botLong(m["message_thread_id"]),
        draftId = botLong(m["draft_id"]) ?: 0,
    )
}

fun messageIdFromMap(raw: Any?): MessageId? {
    val m = raw as? Map<*, *> ?: return null
    return MessageId(
        messageId = botLong(m["message_id"]) ?: 0,
    )
}

fun messageOriginChannelFromMap(raw: Any?): MessageOriginChannel? {
    val m = raw as? Map<*, *> ?: return null
    return MessageOriginChannel(
        type = botString(m["type"]) ?: "",
        date = botLong(m["date"]) ?: 0,
        chat = chatFromMap(m["chat"]),
        messageId = botLong(m["message_id"]) ?: 0,
        authorSignature = botString(m["author_signature"]),
    )
}

fun messageOriginChatFromMap(raw: Any?): MessageOriginChat? {
    val m = raw as? Map<*, *> ?: return null
    return MessageOriginChat(
        type = botString(m["type"]) ?: "",
        date = botLong(m["date"]) ?: 0,
        senderChat = chatFromMap(m["sender_chat"]),
        authorSignature = botString(m["author_signature"]),
    )
}

fun messageOriginHiddenUserFromMap(raw: Any?): MessageOriginHiddenUser? {
    val m = raw as? Map<*, *> ?: return null
    return MessageOriginHiddenUser(
        type = botString(m["type"]) ?: "",
        date = botLong(m["date"]) ?: 0,
        senderUserName = botString(m["sender_user_name"]) ?: "",
    )
}

fun messageOriginUserFromMap(raw: Any?): MessageOriginUser? {
    val m = raw as? Map<*, *> ?: return null
    return MessageOriginUser(
        type = botString(m["type"]) ?: "",
        date = botLong(m["date"]) ?: 0,
        senderUser = userFromMap(m["sender_user"]),
    )
}

fun messageReactionCountUpdatedFromMap(raw: Any?): MessageReactionCountUpdated? {
    val m = raw as? Map<*, *> ?: return null
    return MessageReactionCountUpdated(
        chat = chatFromMap(m["chat"]),
        messageId = botLong(m["message_id"]) ?: 0,
        date = botLong(m["date"]) ?: 0,
        reactions = botList(m["reactions"]) { reactionCountFromMap(it) } ?: emptyList(),
    )
}

fun messageReactionUpdatedFromMap(raw: Any?): MessageReactionUpdated? {
    val m = raw as? Map<*, *> ?: return null
    return MessageReactionUpdated(
        chat = chatFromMap(m["chat"]),
        messageId = botLong(m["message_id"]) ?: 0,
        user = userFromMap(m["user"]),
        actorChat = chatFromMap(m["actor_chat"]),
        date = botLong(m["date"]) ?: 0,
        oldReaction = botList(m["old_reaction"]) { reactionTypeFromMap(it) } ?: emptyList(),
        newReaction = botList(m["new_reaction"]) { reactionTypeFromMap(it) } ?: emptyList(),
    )
}

fun orderInfoFromMap(raw: Any?): OrderInfo? {
    val m = raw as? Map<*, *> ?: return null
    return OrderInfo(
        name = botString(m["name"]),
        phoneNumber = botString(m["phone_number"]),
        email = botString(m["email"]),
        shippingAddress = shippingAddressFromMap(m["shipping_address"]),
    )
}

fun ownedGiftRegularFromMap(raw: Any?): OwnedGiftRegular? {
    val m = raw as? Map<*, *> ?: return null
    return OwnedGiftRegular(
        type = botString(m["type"]) ?: "",
        gift = giftFromMap(m["gift"]),
        ownedGiftId = botString(m["owned_gift_id"]),
        senderUser = userFromMap(m["sender_user"]),
        sendDate = botLong(m["send_date"]) ?: 0,
        text = botString(m["text"]),
        entities = botList(m["entities"]) { messageEntityFromMap(it) },
        isPrivate = botBool(m["is_private"]),
        isSaved = botBool(m["is_saved"]),
        canBeUpgraded = botBool(m["can_be_upgraded"]),
        wasRefunded = botBool(m["was_refunded"]),
        convertStarCount = botLong(m["convert_star_count"]),
        prepaidUpgradeStarCount = botLong(m["prepaid_upgrade_star_count"]),
        isUpgradeSeparate = botBool(m["is_upgrade_separate"]),
        uniqueGiftNumber = botLong(m["unique_gift_number"]),
    )
}

fun ownedGiftUniqueFromMap(raw: Any?): OwnedGiftUnique? {
    val m = raw as? Map<*, *> ?: return null
    return OwnedGiftUnique(
        type = botString(m["type"]) ?: "",
        gift = uniqueGiftFromMap(m["gift"]),
        ownedGiftId = botString(m["owned_gift_id"]),
        senderUser = userFromMap(m["sender_user"]),
        sendDate = botLong(m["send_date"]) ?: 0,
        isSaved = botBool(m["is_saved"]),
        canBeTransferred = botBool(m["can_be_transferred"]),
        transferStarCount = botLong(m["transfer_star_count"]),
        nextTransferDate = botLong(m["next_transfer_date"]),
    )
}

fun ownedGiftsFromMap(raw: Any?): OwnedGifts? {
    val m = raw as? Map<*, *> ?: return null
    return OwnedGifts(
        totalCount = botLong(m["total_count"]) ?: 0,
        gifts = botList(m["gifts"]) { ownedGiftFromMap(it) } ?: emptyList(),
        nextOffset = botString(m["next_offset"]),
    )
}

fun paidMediaInfoFromMap(raw: Any?): PaidMediaInfo? {
    val m = raw as? Map<*, *> ?: return null
    return PaidMediaInfo(
        starCount = botLong(m["star_count"]) ?: 0,
        paidMedia = botList(m["paid_media"]) { paidMediaFromMap(it) } ?: emptyList(),
    )
}

fun paidMediaLivePhotoFromMap(raw: Any?): PaidMediaLivePhoto? {
    val m = raw as? Map<*, *> ?: return null
    return PaidMediaLivePhoto(
        type = botString(m["type"]) ?: "",
        livePhoto = livePhotoFromMap(m["live_photo"]),
    )
}

fun paidMediaPhotoFromMap(raw: Any?): PaidMediaPhoto? {
    val m = raw as? Map<*, *> ?: return null
    return PaidMediaPhoto(
        type = botString(m["type"]) ?: "",
        photo = botList(m["photo"]) { photoSizeFromMap(it) } ?: emptyList(),
    )
}

fun paidMediaPreviewFromMap(raw: Any?): PaidMediaPreview? {
    val m = raw as? Map<*, *> ?: return null
    return PaidMediaPreview(
        type = botString(m["type"]) ?: "",
        width = botLong(m["width"]),
        height = botLong(m["height"]),
        duration = botLong(m["duration"]),
    )
}

fun paidMediaPurchasedFromMap(raw: Any?): PaidMediaPurchased? {
    val m = raw as? Map<*, *> ?: return null
    return PaidMediaPurchased(
        from = userFromMap(m["from"]),
        paidMediaPayload = botString(m["paid_media_payload"]) ?: "",
    )
}

fun paidMediaVideoFromMap(raw: Any?): PaidMediaVideo? {
    val m = raw as? Map<*, *> ?: return null
    return PaidMediaVideo(
        type = botString(m["type"]) ?: "",
        video = videoFromMap(m["video"]),
    )
}

fun paidMessagePriceChangedFromMap(raw: Any?): PaidMessagePriceChanged? {
    val m = raw as? Map<*, *> ?: return null
    return PaidMessagePriceChanged(
        paidMessageStarCount = botLong(m["paid_message_star_count"]) ?: 0,
    )
}

fun passportDataFromMap(raw: Any?): PassportData? {
    val m = raw as? Map<*, *> ?: return null
    return PassportData(
        data = botList(m["data"]) { encryptedPassportElementFromMap(it) } ?: emptyList(),
        credentials = encryptedCredentialsFromMap(m["credentials"]),
    )
}

fun passportElementErrorDataFieldFromMap(raw: Any?): PassportElementErrorDataField? {
    val m = raw as? Map<*, *> ?: return null
    return PassportElementErrorDataField(
        source = botString(m["source"]) ?: "",
        type = botString(m["type"]) ?: "",
        fieldName = botString(m["field_name"]) ?: "",
        dataHash = botString(m["data_hash"]) ?: "",
        message = botString(m["message"]) ?: "",
    )
}

fun passportElementErrorFileFromMap(raw: Any?): PassportElementErrorFile? {
    val m = raw as? Map<*, *> ?: return null
    return PassportElementErrorFile(
        source = botString(m["source"]) ?: "",
        type = botString(m["type"]) ?: "",
        fileHash = botString(m["file_hash"]) ?: "",
        message = botString(m["message"]) ?: "",
    )
}

fun passportElementErrorFilesFromMap(raw: Any?): PassportElementErrorFiles? {
    val m = raw as? Map<*, *> ?: return null
    return PassportElementErrorFiles(
        source = botString(m["source"]) ?: "",
        type = botString(m["type"]) ?: "",
        fileHashes = botList(m["file_hashes"]) { botString(it) } ?: emptyList(),
        message = botString(m["message"]) ?: "",
    )
}

fun passportElementErrorFrontSideFromMap(raw: Any?): PassportElementErrorFrontSide? {
    val m = raw as? Map<*, *> ?: return null
    return PassportElementErrorFrontSide(
        source = botString(m["source"]) ?: "",
        type = botString(m["type"]) ?: "",
        fileHash = botString(m["file_hash"]) ?: "",
        message = botString(m["message"]) ?: "",
    )
}

fun passportElementErrorReverseSideFromMap(raw: Any?): PassportElementErrorReverseSide? {
    val m = raw as? Map<*, *> ?: return null
    return PassportElementErrorReverseSide(
        source = botString(m["source"]) ?: "",
        type = botString(m["type"]) ?: "",
        fileHash = botString(m["file_hash"]) ?: "",
        message = botString(m["message"]) ?: "",
    )
}

fun passportElementErrorSelfieFromMap(raw: Any?): PassportElementErrorSelfie? {
    val m = raw as? Map<*, *> ?: return null
    return PassportElementErrorSelfie(
        source = botString(m["source"]) ?: "",
        type = botString(m["type"]) ?: "",
        fileHash = botString(m["file_hash"]) ?: "",
        message = botString(m["message"]) ?: "",
    )
}

fun passportElementErrorTranslationFileFromMap(raw: Any?): PassportElementErrorTranslationFile? {
    val m = raw as? Map<*, *> ?: return null
    return PassportElementErrorTranslationFile(
        source = botString(m["source"]) ?: "",
        type = botString(m["type"]) ?: "",
        fileHash = botString(m["file_hash"]) ?: "",
        message = botString(m["message"]) ?: "",
    )
}

fun passportElementErrorTranslationFilesFromMap(raw: Any?): PassportElementErrorTranslationFiles? {
    val m = raw as? Map<*, *> ?: return null
    return PassportElementErrorTranslationFiles(
        source = botString(m["source"]) ?: "",
        type = botString(m["type"]) ?: "",
        fileHashes = botList(m["file_hashes"]) { botString(it) } ?: emptyList(),
        message = botString(m["message"]) ?: "",
    )
}

fun passportElementErrorUnspecifiedFromMap(raw: Any?): PassportElementErrorUnspecified? {
    val m = raw as? Map<*, *> ?: return null
    return PassportElementErrorUnspecified(
        source = botString(m["source"]) ?: "",
        type = botString(m["type"]) ?: "",
        elementHash = botString(m["element_hash"]) ?: "",
        message = botString(m["message"]) ?: "",
    )
}

fun passportFileFromMap(raw: Any?): PassportFile? {
    val m = raw as? Map<*, *> ?: return null
    return PassportFile(
        fileId = botString(m["file_id"]) ?: "",
        fileUniqueId = botString(m["file_unique_id"]) ?: "",
        fileSize = botLong(m["file_size"]) ?: 0,
        fileDate = botLong(m["file_date"]) ?: 0,
    )
}

fun photoSizeFromMap(raw: Any?): PhotoSize? {
    val m = raw as? Map<*, *> ?: return null
    return PhotoSize(
        fileId = botString(m["file_id"]) ?: "",
        fileUniqueId = botString(m["file_unique_id"]) ?: "",
        width = botLong(m["width"]) ?: 0,
        height = botLong(m["height"]) ?: 0,
        fileSize = botLong(m["file_size"]),
    )
}

fun pollFromMap(raw: Any?): Poll? {
    val m = raw as? Map<*, *> ?: return null
    return Poll(
        id = botString(m["id"]) ?: "",
        question = botString(m["question"]) ?: "",
        questionEntities = botList(m["question_entities"]) { messageEntityFromMap(it) },
        options = botList(m["options"]) { pollOptionFromMap(it) } ?: emptyList(),
        totalVoterCount = botLong(m["total_voter_count"]) ?: 0,
        isClosed = botBool(m["is_closed"]) ?: false,
        isAnonymous = botBool(m["is_anonymous"]) ?: false,
        type = botString(m["type"]) ?: "",
        allowsMultipleAnswers = botBool(m["allows_multiple_answers"]) ?: false,
        allowsRevoting = botBool(m["allows_revoting"]) ?: false,
        membersOnly = botBool(m["members_only"]) ?: false,
        countryCodes = botList(m["country_codes"]) { botString(it) },
        correctOptionIds = botList(m["correct_option_ids"]) { botLong(it) },
        explanation = botString(m["explanation"]),
        explanationEntities = botList(m["explanation_entities"]) { messageEntityFromMap(it) },
        explanationMedia = pollMediaFromMap(m["explanation_media"]),
        openPeriod = botLong(m["open_period"]),
        closeDate = botLong(m["close_date"]),
        description = botString(m["description"]),
        descriptionEntities = botList(m["description_entities"]) { messageEntityFromMap(it) },
        media = pollMediaFromMap(m["media"]),
    )
}

fun pollAnswerFromMap(raw: Any?): PollAnswer? {
    val m = raw as? Map<*, *> ?: return null
    return PollAnswer(
        pollId = botString(m["poll_id"]) ?: "",
        voterChat = chatFromMap(m["voter_chat"]),
        user = userFromMap(m["user"]),
        optionIds = botList(m["option_ids"]) { botLong(it) } ?: emptyList(),
        optionPersistentIds = botList(m["option_persistent_ids"]) { botString(it) } ?: emptyList(),
    )
}

fun pollMediaFromMap(raw: Any?): PollMedia? {
    val m = raw as? Map<*, *> ?: return null
    return PollMedia(
        animation = animationFromMap(m["animation"]),
        audio = audioFromMap(m["audio"]),
        document = documentFromMap(m["document"]),
        link = linkFromMap(m["link"]),
        livePhoto = livePhotoFromMap(m["live_photo"]),
        location = locationFromMap(m["location"]),
        photo = botList(m["photo"]) { photoSizeFromMap(it) },
        sticker = stickerFromMap(m["sticker"]),
        venue = venueFromMap(m["venue"]),
        video = videoFromMap(m["video"]),
    )
}

fun pollOptionFromMap(raw: Any?): PollOption? {
    val m = raw as? Map<*, *> ?: return null
    return PollOption(
        persistentId = botString(m["persistent_id"]) ?: "",
        text = botString(m["text"]) ?: "",
        textEntities = botList(m["text_entities"]) { messageEntityFromMap(it) },
        media = pollMediaFromMap(m["media"]),
        voterCount = botLong(m["voter_count"]) ?: 0,
        addedByUser = userFromMap(m["added_by_user"]),
        addedByChat = chatFromMap(m["added_by_chat"]),
        additionDate = botLong(m["addition_date"]),
    )
}

fun pollOptionAddedFromMap(raw: Any?): PollOptionAdded? {
    val m = raw as? Map<*, *> ?: return null
    return PollOptionAdded(
        pollMessage = maybeInaccessibleMessageFromMap(m["poll_message"]),
        optionPersistentId = botString(m["option_persistent_id"]) ?: "",
        optionText = botString(m["option_text"]) ?: "",
        optionTextEntities = botList(m["option_text_entities"]) { messageEntityFromMap(it) },
    )
}

fun pollOptionDeletedFromMap(raw: Any?): PollOptionDeleted? {
    val m = raw as? Map<*, *> ?: return null
    return PollOptionDeleted(
        pollMessage = maybeInaccessibleMessageFromMap(m["poll_message"]),
        optionPersistentId = botString(m["option_persistent_id"]) ?: "",
        optionText = botString(m["option_text"]) ?: "",
        optionTextEntities = botList(m["option_text_entities"]) { messageEntityFromMap(it) },
    )
}

fun preCheckoutQueryFromMap(raw: Any?): PreCheckoutQuery? {
    val m = raw as? Map<*, *> ?: return null
    return PreCheckoutQuery(
        id = botString(m["id"]) ?: "",
        from = userFromMap(m["from"]),
        currency = botString(m["currency"]) ?: "",
        totalAmount = botLong(m["total_amount"]) ?: 0,
        invoicePayload = botString(m["invoice_payload"]) ?: "",
        shippingOptionId = botString(m["shipping_option_id"]),
        orderInfo = orderInfoFromMap(m["order_info"]),
    )
}

fun preparedInlineMessageFromMap(raw: Any?): PreparedInlineMessage? {
    val m = raw as? Map<*, *> ?: return null
    return PreparedInlineMessage(
        id = botString(m["id"]) ?: "",
        expirationDate = botLong(m["expiration_date"]) ?: 0,
    )
}

fun preparedKeyboardButtonFromMap(raw: Any?): PreparedKeyboardButton? {
    val m = raw as? Map<*, *> ?: return null
    return PreparedKeyboardButton(
        id = botString(m["id"]) ?: "",
    )
}

fun proximityAlertTriggeredFromMap(raw: Any?): ProximityAlertTriggered? {
    val m = raw as? Map<*, *> ?: return null
    return ProximityAlertTriggered(
        traveler = userFromMap(m["traveler"]),
        watcher = userFromMap(m["watcher"]),
        distance = botLong(m["distance"]) ?: 0,
    )
}

fun reactionCountFromMap(raw: Any?): ReactionCount? {
    val m = raw as? Map<*, *> ?: return null
    return ReactionCount(
        type = reactionTypeFromMap(m["type"]),
        totalCount = botLong(m["total_count"]) ?: 0,
    )
}

fun reactionTypeCustomEmojiFromMap(raw: Any?): ReactionTypeCustomEmoji? {
    val m = raw as? Map<*, *> ?: return null
    return ReactionTypeCustomEmoji(
        type = botString(m["type"]) ?: "",
        customEmojiId = botString(m["custom_emoji_id"]) ?: "",
    )
}

fun reactionTypeEmojiFromMap(raw: Any?): ReactionTypeEmoji? {
    val m = raw as? Map<*, *> ?: return null
    return ReactionTypeEmoji(
        type = botString(m["type"]) ?: "",
        emoji = botString(m["emoji"]) ?: "",
    )
}

fun reactionTypePaidFromMap(raw: Any?): ReactionTypePaid? {
    val m = raw as? Map<*, *> ?: return null
    return ReactionTypePaid(
        type = botString(m["type"]) ?: "",
    )
}

fun refundedPaymentFromMap(raw: Any?): RefundedPayment? {
    val m = raw as? Map<*, *> ?: return null
    return RefundedPayment(
        currency = botString(m["currency"]) ?: "",
        totalAmount = botLong(m["total_amount"]) ?: 0,
        invoicePayload = botString(m["invoice_payload"]) ?: "",
        telegramPaymentChargeId = botString(m["telegram_payment_charge_id"]) ?: "",
        providerPaymentChargeId = botString(m["provider_payment_charge_id"]),
    )
}

fun replyKeyboardMarkupFromMap(raw: Any?): ReplyKeyboardMarkup? {
    val m = raw as? Map<*, *> ?: return null
    return ReplyKeyboardMarkup(
        keyboard = botList(m["keyboard"]) { botList(it) { keyboardButtonFromMap(it) } } ?: emptyList(),
        isPersistent = botBool(m["is_persistent"]),
        resizeKeyboard = botBool(m["resize_keyboard"]),
        oneTimeKeyboard = botBool(m["one_time_keyboard"]),
        inputFieldPlaceholder = botString(m["input_field_placeholder"]),
        selective = botBool(m["selective"]),
        forceReply = botBool(m["force_reply"]),
    )
}

fun replyKeyboardRemoveFromMap(raw: Any?): ReplyKeyboardRemove? {
    val m = raw as? Map<*, *> ?: return null
    return ReplyKeyboardRemove(
        removeKeyboard = botBool(m["remove_keyboard"]) ?: false,
        selective = botBool(m["selective"]),
    )
}

fun replyParametersFromMap(raw: Any?): ReplyParameters? {
    val m = raw as? Map<*, *> ?: return null
    return ReplyParameters(
        messageId = botLong(m["message_id"]),
        chatId = LongOrString.of(m["chat_id"]),
        ephemeralMessageId = botLong(m["ephemeral_message_id"]),
        allowSendingWithoutReply = botBool(m["allow_sending_without_reply"]),
        quote = botString(m["quote"]),
        quoteParseMode = botString(m["quote_parse_mode"]),
        quoteEntities = botList(m["quote_entities"]) { messageEntityFromMap(it) },
        quotePosition = botLong(m["quote_position"]),
        checklistTaskId = botLong(m["checklist_task_id"]),
        pollOptionId = botString(m["poll_option_id"]),
    )
}

fun responseParametersFromMap(raw: Any?): ResponseParameters? {
    val m = raw as? Map<*, *> ?: return null
    return ResponseParameters(
        migrateToChatId = botLong(m["migrate_to_chat_id"]),
        retryAfter = botLong(m["retry_after"]),
    )
}

fun revenueWithdrawalStateFailedFromMap(raw: Any?): RevenueWithdrawalStateFailed? {
    val m = raw as? Map<*, *> ?: return null
    return RevenueWithdrawalStateFailed(
        type = botString(m["type"]) ?: "",
    )
}

fun revenueWithdrawalStatePendingFromMap(raw: Any?): RevenueWithdrawalStatePending? {
    val m = raw as? Map<*, *> ?: return null
    return RevenueWithdrawalStatePending(
        type = botString(m["type"]) ?: "",
    )
}

fun revenueWithdrawalStateSucceededFromMap(raw: Any?): RevenueWithdrawalStateSucceeded? {
    val m = raw as? Map<*, *> ?: return null
    return RevenueWithdrawalStateSucceeded(
        type = botString(m["type"]) ?: "",
        date = botLong(m["date"]) ?: 0,
        url = botString(m["url"]) ?: "",
    )
}

fun richBlockAnchorFromMap(raw: Any?): RichBlockAnchor? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockAnchor(
        type = botString(m["type"]) ?: "",
        name = botString(m["name"]) ?: "",
    )
}

fun richBlockAnimationFromMap(raw: Any?): RichBlockAnimation? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockAnimation(
        type = botString(m["type"]) ?: "",
        animation = animationFromMap(m["animation"]),
        hasSpoiler = botBool(m["has_spoiler"]),
        caption = richBlockCaptionFromMap(m["caption"]),
    )
}

fun richBlockAudioFromMap(raw: Any?): RichBlockAudio? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockAudio(
        type = botString(m["type"]) ?: "",
        audio = audioFromMap(m["audio"]),
        caption = richBlockCaptionFromMap(m["caption"]),
    )
}

fun richBlockBlockQuotationFromMap(raw: Any?): RichBlockBlockQuotation? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockBlockQuotation(
        type = botString(m["type"]) ?: "",
        blocks = botList(m["blocks"]) { richBlockFromMap(it) } ?: emptyList(),
        credit = richTextFromMap(m["credit"]),
    )
}

fun richBlockButtonsFromMap(raw: Any?): RichBlockButtons? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockButtons(
        type = botString(m["type"]) ?: "",
        buttons = botList(m["buttons"]) { richMessageButtonFromMap(it) } ?: emptyList(),
        align = botString(m["align"]),
    )
}

fun richBlockCaptionFromMap(raw: Any?): RichBlockCaption? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockCaption(
        text = richTextFromMap(m["text"]),
        credit = richTextFromMap(m["credit"]),
    )
}

fun richBlockCollageFromMap(raw: Any?): RichBlockCollage? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockCollage(
        type = botString(m["type"]) ?: "",
        blocks = botList(m["blocks"]) { richBlockFromMap(it) } ?: emptyList(),
        caption = richBlockCaptionFromMap(m["caption"]),
    )
}

fun richBlockDetailsFromMap(raw: Any?): RichBlockDetails? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockDetails(
        type = botString(m["type"]) ?: "",
        summary = richTextFromMap(m["summary"]),
        blocks = botList(m["blocks"]) { richBlockFromMap(it) } ?: emptyList(),
        isOpen = botBool(m["is_open"]),
    )
}

fun richBlockDividerFromMap(raw: Any?): RichBlockDivider? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockDivider(
        type = botString(m["type"]) ?: "",
    )
}

fun richBlockDocumentFromMap(raw: Any?): RichBlockDocument? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockDocument(
        type = botString(m["type"]) ?: "",
        document = documentFromMap(m["document"]),
        caption = richBlockCaptionFromMap(m["caption"]),
    )
}

fun richBlockExpandableBlockQuotationFromMap(raw: Any?): RichBlockExpandableBlockQuotation? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockExpandableBlockQuotation(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
        credit = richTextFromMap(m["credit"]),
    )
}

fun richBlockFooterFromMap(raw: Any?): RichBlockFooter? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockFooter(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
    )
}

fun richBlockListFromMap(raw: Any?): RichBlockList? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockList(
        type = botString(m["type"]) ?: "",
        items = botList(m["items"]) { richBlockListItemFromMap(it) } ?: emptyList(),
    )
}

fun richBlockListItemFromMap(raw: Any?): RichBlockListItem? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockListItem(
        label = botString(m["label"]) ?: "",
        blocks = botList(m["blocks"]) { richBlockFromMap(it) } ?: emptyList(),
        hasCheckbox = botBool(m["has_checkbox"]),
        isChecked = botBool(m["is_checked"]),
        value = botLong(m["value"]),
        type = botString(m["type"]),
    )
}

fun richBlockMapFromMap(raw: Any?): RichBlockMap? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockMap(
        type = botString(m["type"]) ?: "",
        location = locationFromMap(m["location"]),
        zoom = botLong(m["zoom"]) ?: 0,
        width = botLong(m["width"]) ?: 0,
        height = botLong(m["height"]) ?: 0,
        caption = richBlockCaptionFromMap(m["caption"]),
    )
}

fun richBlockMathematicalExpressionFromMap(raw: Any?): RichBlockMathematicalExpression? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockMathematicalExpression(
        type = botString(m["type"]) ?: "",
        expression = botString(m["expression"]) ?: "",
    )
}

fun richBlockParagraphFromMap(raw: Any?): RichBlockParagraph? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockParagraph(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
    )
}

fun richBlockPhotoFromMap(raw: Any?): RichBlockPhoto? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockPhoto(
        type = botString(m["type"]) ?: "",
        photo = botList(m["photo"]) { photoSizeFromMap(it) } ?: emptyList(),
        hasSpoiler = botBool(m["has_spoiler"]),
        caption = richBlockCaptionFromMap(m["caption"]),
    )
}

fun richBlockPreformattedFromMap(raw: Any?): RichBlockPreformatted? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockPreformatted(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
        language = botString(m["language"]),
    )
}

fun richBlockPullQuotationFromMap(raw: Any?): RichBlockPullQuotation? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockPullQuotation(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
        credit = richTextFromMap(m["credit"]),
    )
}

fun richBlockSectionHeadingFromMap(raw: Any?): RichBlockSectionHeading? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockSectionHeading(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
        size = botLong(m["size"]) ?: 0,
    )
}

fun richBlockSlideshowFromMap(raw: Any?): RichBlockSlideshow? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockSlideshow(
        type = botString(m["type"]) ?: "",
        blocks = botList(m["blocks"]) { richBlockFromMap(it) } ?: emptyList(),
        caption = richBlockCaptionFromMap(m["caption"]),
    )
}

fun richBlockTableFromMap(raw: Any?): RichBlockTable? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockTable(
        type = botString(m["type"]) ?: "",
        cells = botList(m["cells"]) { botList(it) { richBlockTableCellFromMap(it) } } ?: emptyList(),
        isBordered = botBool(m["is_bordered"]),
        isStriped = botBool(m["is_striped"]),
        isCompact = botBool(m["is_compact"]),
        caption = richTextFromMap(m["caption"]),
    )
}

fun richBlockTableCellFromMap(raw: Any?): RichBlockTableCell? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockTableCell(
        text = richTextFromMap(m["text"]),
        isHeader = botBool(m["is_header"]),
        colspan = botLong(m["colspan"]),
        rowspan = botLong(m["rowspan"]),
        align = botString(m["align"]) ?: "",
        valign = botString(m["valign"]) ?: "",
    )
}

fun richBlockThinkingFromMap(raw: Any?): RichBlockThinking? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockThinking(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
    )
}

fun richBlockVideoFromMap(raw: Any?): RichBlockVideo? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockVideo(
        type = botString(m["type"]) ?: "",
        video = videoFromMap(m["video"]),
        hasSpoiler = botBool(m["has_spoiler"]),
        caption = richBlockCaptionFromMap(m["caption"]),
    )
}

fun richBlockVoiceNoteFromMap(raw: Any?): RichBlockVoiceNote? {
    val m = raw as? Map<*, *> ?: return null
    return RichBlockVoiceNote(
        type = botString(m["type"]) ?: "",
        voiceNote = voiceFromMap(m["voice_note"]),
        caption = richBlockCaptionFromMap(m["caption"]),
    )
}

fun richMessageFromMap(raw: Any?): RichMessage? {
    val m = raw as? Map<*, *> ?: return null
    return RichMessage(
        blocks = botList(m["blocks"]) { richBlockFromMap(it) } ?: emptyList(),
        isRtl = botBool(m["is_rtl"]),
    )
}

fun richMessageButtonFromMap(raw: Any?): RichMessageButton? {
    val m = raw as? Map<*, *> ?: return null
    return RichMessageButton(
        text = richTextFromMap(m["text"]),
        style = botString(m["style"]),
        url = botString(m["url"]),
        callbackData = botString(m["callback_data"]),
        webApp = webAppInfoFromMap(m["web_app"]),
        loginUrl = loginUrlFromMap(m["login_url"]),
        switchInlineQuery = botString(m["switch_inline_query"]),
        switchInlineQueryCurrentChat = botString(m["switch_inline_query_current_chat"]),
        switchInlineQueryChosenChat = switchInlineQueryChosenChatFromMap(m["switch_inline_query_chosen_chat"]),
        copyText = copyTextButtonFromMap(m["copy_text"]),
        disabled = disabledButtonFromMap(m["disabled"]),
    )
}

fun richTextAnchorFromMap(raw: Any?): RichTextAnchor? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextAnchor(
        type = botString(m["type"]) ?: "",
        name = botString(m["name"]) ?: "",
    )
}

fun richTextAnchorLinkFromMap(raw: Any?): RichTextAnchorLink? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextAnchorLink(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
        anchorName = botString(m["anchor_name"]) ?: "",
    )
}

fun richTextBankCardNumberFromMap(raw: Any?): RichTextBankCardNumber? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextBankCardNumber(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
        bankCardNumber = botString(m["bank_card_number"]) ?: "",
    )
}

fun richTextBoldFromMap(raw: Any?): RichTextBold? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextBold(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
    )
}

fun richTextBotCommandFromMap(raw: Any?): RichTextBotCommand? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextBotCommand(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
        botCommand = botString(m["bot_command"]) ?: "",
    )
}

fun richTextButtonFromMap(raw: Any?): RichTextButton? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextButton(
        type = botString(m["type"]) ?: "",
        button = richMessageButtonFromMap(m["button"]),
    )
}

fun richTextCashtagFromMap(raw: Any?): RichTextCashtag? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextCashtag(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
        cashtag = botString(m["cashtag"]) ?: "",
    )
}

fun richTextCodeFromMap(raw: Any?): RichTextCode? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextCode(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
    )
}

fun richTextCustomEmojiFromMap(raw: Any?): RichTextCustomEmoji? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextCustomEmoji(
        type = botString(m["type"]) ?: "",
        customEmojiId = botString(m["custom_emoji_id"]) ?: "",
        alternativeText = botString(m["alternative_text"]) ?: "",
    )
}

fun richTextDateTimeFromMap(raw: Any?): RichTextDateTime? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextDateTime(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
        unixTime = botLong(m["unix_time"]) ?: 0,
        dateTimeFormat = botString(m["date_time_format"]) ?: "",
    )
}

fun richTextEmailAddressFromMap(raw: Any?): RichTextEmailAddress? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextEmailAddress(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
        emailAddress = botString(m["email_address"]) ?: "",
    )
}

fun richTextHashtagFromMap(raw: Any?): RichTextHashtag? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextHashtag(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
        hashtag = botString(m["hashtag"]) ?: "",
    )
}

fun richTextItalicFromMap(raw: Any?): RichTextItalic? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextItalic(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
    )
}

fun richTextMarkedFromMap(raw: Any?): RichTextMarked? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextMarked(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
    )
}

fun richTextMathematicalExpressionFromMap(raw: Any?): RichTextMathematicalExpression? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextMathematicalExpression(
        type = botString(m["type"]) ?: "",
        expression = botString(m["expression"]) ?: "",
    )
}

fun richTextMentionFromMap(raw: Any?): RichTextMention? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextMention(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
        username = botString(m["username"]) ?: "",
    )
}

fun richTextPhoneNumberFromMap(raw: Any?): RichTextPhoneNumber? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextPhoneNumber(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
        phoneNumber = botString(m["phone_number"]) ?: "",
    )
}

fun richTextReferenceFromMap(raw: Any?): RichTextReference? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextReference(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
        name = botString(m["name"]) ?: "",
    )
}

fun richTextReferenceLinkFromMap(raw: Any?): RichTextReferenceLink? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextReferenceLink(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
        referenceName = botString(m["reference_name"]) ?: "",
    )
}

fun richTextSpoilerFromMap(raw: Any?): RichTextSpoiler? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextSpoiler(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
    )
}

fun richTextStrikethroughFromMap(raw: Any?): RichTextStrikethrough? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextStrikethrough(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
    )
}

fun richTextSubscriptFromMap(raw: Any?): RichTextSubscript? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextSubscript(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
    )
}

fun richTextSuperscriptFromMap(raw: Any?): RichTextSuperscript? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextSuperscript(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
    )
}

fun richTextTextMentionFromMap(raw: Any?): RichTextTextMention? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextTextMention(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
        user = userFromMap(m["user"]),
    )
}

fun richTextUnderlineFromMap(raw: Any?): RichTextUnderline? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextUnderline(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
    )
}

fun richTextUrlFromMap(raw: Any?): RichTextUrl? {
    val m = raw as? Map<*, *> ?: return null
    return RichTextUrl(
        type = botString(m["type"]) ?: "",
        text = richTextFromMap(m["text"]),
        url = botString(m["url"]) ?: "",
    )
}

fun sentGuestMessageFromMap(raw: Any?): SentGuestMessage? {
    val m = raw as? Map<*, *> ?: return null
    return SentGuestMessage(
        inlineMessageId = botString(m["inline_message_id"]) ?: "",
    )
}

fun sentWebAppMessageFromMap(raw: Any?): SentWebAppMessage? {
    val m = raw as? Map<*, *> ?: return null
    return SentWebAppMessage(
        inlineMessageId = botString(m["inline_message_id"]),
    )
}

fun sharedUserFromMap(raw: Any?): SharedUser? {
    val m = raw as? Map<*, *> ?: return null
    return SharedUser(
        userId = botLong(m["user_id"]) ?: 0,
        firstName = botString(m["first_name"]),
        lastName = botString(m["last_name"]),
        username = botString(m["username"]),
        photo = botList(m["photo"]) { photoSizeFromMap(it) },
    )
}

fun shippingAddressFromMap(raw: Any?): ShippingAddress? {
    val m = raw as? Map<*, *> ?: return null
    return ShippingAddress(
        countryCode = botString(m["country_code"]) ?: "",
        state = botString(m["state"]) ?: "",
        city = botString(m["city"]) ?: "",
        streetLine1 = botString(m["street_line1"]) ?: "",
        streetLine2 = botString(m["street_line2"]) ?: "",
        postCode = botString(m["post_code"]) ?: "",
    )
}

fun shippingOptionFromMap(raw: Any?): ShippingOption? {
    val m = raw as? Map<*, *> ?: return null
    return ShippingOption(
        id = botString(m["id"]) ?: "",
        title = botString(m["title"]) ?: "",
        prices = botList(m["prices"]) { labeledPriceFromMap(it) } ?: emptyList(),
    )
}

fun shippingQueryFromMap(raw: Any?): ShippingQuery? {
    val m = raw as? Map<*, *> ?: return null
    return ShippingQuery(
        id = botString(m["id"]) ?: "",
        from = userFromMap(m["from"]),
        invoicePayload = botString(m["invoice_payload"]) ?: "",
        shippingAddress = shippingAddressFromMap(m["shipping_address"]),
    )
}

fun starAmountFromMap(raw: Any?): StarAmount? {
    val m = raw as? Map<*, *> ?: return null
    return StarAmount(
        amount = botLong(m["amount"]) ?: 0,
        nanostarAmount = botLong(m["nanostar_amount"]),
    )
}

fun starTransactionFromMap(raw: Any?): StarTransaction? {
    val m = raw as? Map<*, *> ?: return null
    return StarTransaction(
        id = botString(m["id"]) ?: "",
        amount = botLong(m["amount"]) ?: 0,
        nanostarAmount = botLong(m["nanostar_amount"]),
        date = botLong(m["date"]) ?: 0,
        source = transactionPartnerFromMap(m["source"]),
        receiver = transactionPartnerFromMap(m["receiver"]),
    )
}

fun starTransactionsFromMap(raw: Any?): StarTransactions? {
    val m = raw as? Map<*, *> ?: return null
    return StarTransactions(
        transactions = botList(m["transactions"]) { starTransactionFromMap(it) } ?: emptyList(),
    )
}

fun stickerFromMap(raw: Any?): Sticker? {
    val m = raw as? Map<*, *> ?: return null
    return Sticker(
        fileId = botString(m["file_id"]) ?: "",
        fileUniqueId = botString(m["file_unique_id"]) ?: "",
        type = botString(m["type"]) ?: "",
        width = botLong(m["width"]) ?: 0,
        height = botLong(m["height"]) ?: 0,
        isAnimated = botBool(m["is_animated"]) ?: false,
        isVideo = botBool(m["is_video"]) ?: false,
        thumbnail = photoSizeFromMap(m["thumbnail"]),
        emoji = botString(m["emoji"]),
        setName = botString(m["set_name"]),
        premiumAnimation = fileFromMap(m["premium_animation"]),
        maskPosition = maskPositionFromMap(m["mask_position"]),
        customEmojiId = botString(m["custom_emoji_id"]),
        needsRepainting = botBool(m["needs_repainting"]),
        fileSize = botLong(m["file_size"]),
    )
}

fun stickerSetFromMap(raw: Any?): StickerSet? {
    val m = raw as? Map<*, *> ?: return null
    return StickerSet(
        name = botString(m["name"]) ?: "",
        title = botString(m["title"]) ?: "",
        stickerType = botString(m["sticker_type"]) ?: "",
        stickers = botList(m["stickers"]) { stickerFromMap(it) } ?: emptyList(),
        thumbnail = photoSizeFromMap(m["thumbnail"]),
    )
}

fun storyFromMap(raw: Any?): Story? {
    val m = raw as? Map<*, *> ?: return null
    return Story(
        chat = chatFromMap(m["chat"]),
        id = botLong(m["id"]) ?: 0,
    )
}

fun storyAreaFromMap(raw: Any?): StoryArea? {
    val m = raw as? Map<*, *> ?: return null
    return StoryArea(
        position = storyAreaPositionFromMap(m["position"]),
        type = storyAreaTypeFromMap(m["type"]),
    )
}

fun storyAreaPositionFromMap(raw: Any?): StoryAreaPosition? {
    val m = raw as? Map<*, *> ?: return null
    return StoryAreaPosition(
        xPercentage = botDouble(m["x_percentage"]) ?: 0.0,
        yPercentage = botDouble(m["y_percentage"]) ?: 0.0,
        widthPercentage = botDouble(m["width_percentage"]) ?: 0.0,
        heightPercentage = botDouble(m["height_percentage"]) ?: 0.0,
        rotationAngle = botDouble(m["rotation_angle"]) ?: 0.0,
        cornerRadiusPercentage = botDouble(m["corner_radius_percentage"]) ?: 0.0,
    )
}

fun storyAreaTypeLinkFromMap(raw: Any?): StoryAreaTypeLink? {
    val m = raw as? Map<*, *> ?: return null
    return StoryAreaTypeLink(
        type = botString(m["type"]) ?: "",
        url = botString(m["url"]) ?: "",
    )
}

fun storyAreaTypeLocationFromMap(raw: Any?): StoryAreaTypeLocation? {
    val m = raw as? Map<*, *> ?: return null
    return StoryAreaTypeLocation(
        type = botString(m["type"]) ?: "",
        latitude = botDouble(m["latitude"]) ?: 0.0,
        longitude = botDouble(m["longitude"]) ?: 0.0,
        address = locationAddressFromMap(m["address"]),
    )
}

fun storyAreaTypeSuggestedReactionFromMap(raw: Any?): StoryAreaTypeSuggestedReaction? {
    val m = raw as? Map<*, *> ?: return null
    return StoryAreaTypeSuggestedReaction(
        type = botString(m["type"]) ?: "",
        reactionType = reactionTypeFromMap(m["reaction_type"]),
        isDark = botBool(m["is_dark"]),
        isFlipped = botBool(m["is_flipped"]),
    )
}

fun storyAreaTypeUniqueGiftFromMap(raw: Any?): StoryAreaTypeUniqueGift? {
    val m = raw as? Map<*, *> ?: return null
    return StoryAreaTypeUniqueGift(
        type = botString(m["type"]) ?: "",
        name = botString(m["name"]) ?: "",
    )
}

fun storyAreaTypeWeatherFromMap(raw: Any?): StoryAreaTypeWeather? {
    val m = raw as? Map<*, *> ?: return null
    return StoryAreaTypeWeather(
        type = botString(m["type"]) ?: "",
        temperature = botDouble(m["temperature"]) ?: 0.0,
        emoji = botString(m["emoji"]) ?: "",
        backgroundColor = botLong(m["background_color"]) ?: 0,
    )
}

fun successfulPaymentFromMap(raw: Any?): SuccessfulPayment? {
    val m = raw as? Map<*, *> ?: return null
    return SuccessfulPayment(
        currency = botString(m["currency"]) ?: "",
        totalAmount = botLong(m["total_amount"]) ?: 0,
        invoicePayload = botString(m["invoice_payload"]) ?: "",
        subscriptionExpirationDate = botLong(m["subscription_expiration_date"]),
        isRecurring = botBool(m["is_recurring"]),
        isFirstRecurring = botBool(m["is_first_recurring"]),
        shippingOptionId = botString(m["shipping_option_id"]),
        orderInfo = orderInfoFromMap(m["order_info"]),
        telegramPaymentChargeId = botString(m["telegram_payment_charge_id"]) ?: "",
        providerPaymentChargeId = botString(m["provider_payment_charge_id"]) ?: "",
    )
}

fun suggestedPostApprovalFailedFromMap(raw: Any?): SuggestedPostApprovalFailed? {
    val m = raw as? Map<*, *> ?: return null
    return SuggestedPostApprovalFailed(
        suggestedPostMessage = messageFromMap(m["suggested_post_message"]),
        price = suggestedPostPriceFromMap(m["price"]),
    )
}

fun suggestedPostApprovedFromMap(raw: Any?): SuggestedPostApproved? {
    val m = raw as? Map<*, *> ?: return null
    return SuggestedPostApproved(
        suggestedPostMessage = messageFromMap(m["suggested_post_message"]),
        price = suggestedPostPriceFromMap(m["price"]),
        sendDate = botLong(m["send_date"]) ?: 0,
    )
}

fun suggestedPostDeclinedFromMap(raw: Any?): SuggestedPostDeclined? {
    val m = raw as? Map<*, *> ?: return null
    return SuggestedPostDeclined(
        suggestedPostMessage = messageFromMap(m["suggested_post_message"]),
        comment = botString(m["comment"]),
    )
}

fun suggestedPostInfoFromMap(raw: Any?): SuggestedPostInfo? {
    val m = raw as? Map<*, *> ?: return null
    return SuggestedPostInfo(
        state = botString(m["state"]) ?: "",
        price = suggestedPostPriceFromMap(m["price"]),
        sendDate = botLong(m["send_date"]),
    )
}

fun suggestedPostPaidFromMap(raw: Any?): SuggestedPostPaid? {
    val m = raw as? Map<*, *> ?: return null
    return SuggestedPostPaid(
        suggestedPostMessage = messageFromMap(m["suggested_post_message"]),
        currency = botString(m["currency"]) ?: "",
        amount = botLong(m["amount"]),
        starAmount = starAmountFromMap(m["star_amount"]),
    )
}

fun suggestedPostParametersFromMap(raw: Any?): SuggestedPostParameters? {
    val m = raw as? Map<*, *> ?: return null
    return SuggestedPostParameters(
        price = suggestedPostPriceFromMap(m["price"]),
        sendDate = botLong(m["send_date"]),
    )
}

fun suggestedPostPriceFromMap(raw: Any?): SuggestedPostPrice? {
    val m = raw as? Map<*, *> ?: return null
    return SuggestedPostPrice(
        currency = botString(m["currency"]) ?: "",
        amount = botLong(m["amount"]) ?: 0,
    )
}

fun suggestedPostRefundedFromMap(raw: Any?): SuggestedPostRefunded? {
    val m = raw as? Map<*, *> ?: return null
    return SuggestedPostRefunded(
        suggestedPostMessage = messageFromMap(m["suggested_post_message"]),
        reason = botString(m["reason"]) ?: "",
    )
}

fun switchInlineQueryChosenChatFromMap(raw: Any?): SwitchInlineQueryChosenChat? {
    val m = raw as? Map<*, *> ?: return null
    return SwitchInlineQueryChosenChat(
        query = botString(m["query"]),
        allowUserChats = botBool(m["allow_user_chats"]),
        allowBotChats = botBool(m["allow_bot_chats"]),
        allowGroupChats = botBool(m["allow_group_chats"]),
        allowChannelChats = botBool(m["allow_channel_chats"]),
    )
}

fun textQuoteFromMap(raw: Any?): TextQuote? {
    val m = raw as? Map<*, *> ?: return null
    return TextQuote(
        text = botString(m["text"]) ?: "",
        entities = botList(m["entities"]) { messageEntityFromMap(it) },
        position = botLong(m["position"]) ?: 0,
        isManual = botBool(m["is_manual"]),
    )
}

fun transactionPartnerAffiliateProgramFromMap(raw: Any?): TransactionPartnerAffiliateProgram? {
    val m = raw as? Map<*, *> ?: return null
    return TransactionPartnerAffiliateProgram(
        type = botString(m["type"]) ?: "",
        sponsorUser = userFromMap(m["sponsor_user"]),
        commissionPerMille = botLong(m["commission_per_mille"]) ?: 0,
    )
}

fun transactionPartnerChatFromMap(raw: Any?): TransactionPartnerChat? {
    val m = raw as? Map<*, *> ?: return null
    return TransactionPartnerChat(
        type = botString(m["type"]) ?: "",
        chat = chatFromMap(m["chat"]),
        gift = giftFromMap(m["gift"]),
    )
}

fun transactionPartnerFragmentFromMap(raw: Any?): TransactionPartnerFragment? {
    val m = raw as? Map<*, *> ?: return null
    return TransactionPartnerFragment(
        type = botString(m["type"]) ?: "",
        withdrawalState = revenueWithdrawalStateFromMap(m["withdrawal_state"]),
    )
}

fun transactionPartnerOtherFromMap(raw: Any?): TransactionPartnerOther? {
    val m = raw as? Map<*, *> ?: return null
    return TransactionPartnerOther(
        type = botString(m["type"]) ?: "",
    )
}

fun transactionPartnerTelegramAdsFromMap(raw: Any?): TransactionPartnerTelegramAds? {
    val m = raw as? Map<*, *> ?: return null
    return TransactionPartnerTelegramAds(
        type = botString(m["type"]) ?: "",
    )
}

fun transactionPartnerTelegramApiFromMap(raw: Any?): TransactionPartnerTelegramApi? {
    val m = raw as? Map<*, *> ?: return null
    return TransactionPartnerTelegramApi(
        type = botString(m["type"]) ?: "",
        requestCount = botLong(m["request_count"]) ?: 0,
    )
}

fun transactionPartnerUserFromMap(raw: Any?): TransactionPartnerUser? {
    val m = raw as? Map<*, *> ?: return null
    return TransactionPartnerUser(
        type = botString(m["type"]) ?: "",
        transactionType = botString(m["transaction_type"]) ?: "",
        user = userFromMap(m["user"]),
        affiliate = affiliateInfoFromMap(m["affiliate"]),
        invoicePayload = botString(m["invoice_payload"]),
        subscriptionPeriod = botLong(m["subscription_period"]),
        paidMedia = botList(m["paid_media"]) { paidMediaFromMap(it) },
        paidMediaPayload = botString(m["paid_media_payload"]),
        gift = giftFromMap(m["gift"]),
        premiumSubscriptionDuration = botLong(m["premium_subscription_duration"]),
    )
}

fun uniqueGiftFromMap(raw: Any?): UniqueGift? {
    val m = raw as? Map<*, *> ?: return null
    return UniqueGift(
        giftId = botString(m["gift_id"]) ?: "",
        baseName = botString(m["base_name"]) ?: "",
        name = botString(m["name"]) ?: "",
        number = botLong(m["number"]) ?: 0,
        model = uniqueGiftModelFromMap(m["model"]),
        symbol = uniqueGiftSymbolFromMap(m["symbol"]),
        backdrop = uniqueGiftBackdropFromMap(m["backdrop"]),
        isPremium = botBool(m["is_premium"]),
        isBurned = botBool(m["is_burned"]),
        isFromBlockchain = botBool(m["is_from_blockchain"]),
        colors = uniqueGiftColorsFromMap(m["colors"]),
        publisherChat = chatFromMap(m["publisher_chat"]),
    )
}

fun uniqueGiftBackdropFromMap(raw: Any?): UniqueGiftBackdrop? {
    val m = raw as? Map<*, *> ?: return null
    return UniqueGiftBackdrop(
        name = botString(m["name"]) ?: "",
        colors = uniqueGiftBackdropColorsFromMap(m["colors"]),
        rarityPerMille = botLong(m["rarity_per_mille"]) ?: 0,
    )
}

fun uniqueGiftBackdropColorsFromMap(raw: Any?): UniqueGiftBackdropColors? {
    val m = raw as? Map<*, *> ?: return null
    return UniqueGiftBackdropColors(
        centerColor = botLong(m["center_color"]) ?: 0,
        edgeColor = botLong(m["edge_color"]) ?: 0,
        symbolColor = botLong(m["symbol_color"]) ?: 0,
        textColor = botLong(m["text_color"]) ?: 0,
    )
}

fun uniqueGiftColorsFromMap(raw: Any?): UniqueGiftColors? {
    val m = raw as? Map<*, *> ?: return null
    return UniqueGiftColors(
        modelCustomEmojiId = botString(m["model_custom_emoji_id"]) ?: "",
        symbolCustomEmojiId = botString(m["symbol_custom_emoji_id"]) ?: "",
        lightThemeMainColor = botLong(m["light_theme_main_color"]) ?: 0,
        lightThemeOtherColors = botList(m["light_theme_other_colors"]) { botLong(it) } ?: emptyList(),
        darkThemeMainColor = botLong(m["dark_theme_main_color"]) ?: 0,
        darkThemeOtherColors = botList(m["dark_theme_other_colors"]) { botLong(it) } ?: emptyList(),
    )
}

fun uniqueGiftInfoFromMap(raw: Any?): UniqueGiftInfo? {
    val m = raw as? Map<*, *> ?: return null
    return UniqueGiftInfo(
        gift = uniqueGiftFromMap(m["gift"]),
        origin = botString(m["origin"]) ?: "",
        text = botString(m["text"]),
        entities = botList(m["entities"]) { messageEntityFromMap(it) },
        isPrivate = botBool(m["is_private"]),
        lastResaleCurrency = botString(m["last_resale_currency"]),
        lastResaleAmount = botLong(m["last_resale_amount"]),
        ownedGiftId = botString(m["owned_gift_id"]),
        transferStarCount = botLong(m["transfer_star_count"]),
        nextTransferDate = botLong(m["next_transfer_date"]),
    )
}

fun uniqueGiftModelFromMap(raw: Any?): UniqueGiftModel? {
    val m = raw as? Map<*, *> ?: return null
    return UniqueGiftModel(
        name = botString(m["name"]) ?: "",
        sticker = stickerFromMap(m["sticker"]),
        rarityPerMille = botLong(m["rarity_per_mille"]) ?: 0,
        rarity = botString(m["rarity"]),
    )
}

fun uniqueGiftSymbolFromMap(raw: Any?): UniqueGiftSymbol? {
    val m = raw as? Map<*, *> ?: return null
    return UniqueGiftSymbol(
        name = botString(m["name"]) ?: "",
        sticker = stickerFromMap(m["sticker"]),
        rarityPerMille = botLong(m["rarity_per_mille"]) ?: 0,
    )
}

fun updateFromMap(raw: Any?): Update? {
    val m = raw as? Map<*, *> ?: return null
    return Update(
        updateId = botLong(m["update_id"]) ?: 0,
        message = messageFromMap(m["message"]),
        editedMessage = messageFromMap(m["edited_message"]),
        channelPost = messageFromMap(m["channel_post"]),
        editedChannelPost = messageFromMap(m["edited_channel_post"]),
        businessConnection = businessConnectionFromMap(m["business_connection"]),
        businessMessage = messageFromMap(m["business_message"]),
        editedBusinessMessage = messageFromMap(m["edited_business_message"]),
        deletedBusinessMessages = businessMessagesDeletedFromMap(m["deleted_business_messages"]),
        guestMessage = messageFromMap(m["guest_message"]),
        messageReaction = messageReactionUpdatedFromMap(m["message_reaction"]),
        messageReactionCount = messageReactionCountUpdatedFromMap(m["message_reaction_count"]),
        inlineQuery = inlineQueryFromMap(m["inline_query"]),
        chosenInlineResult = chosenInlineResultFromMap(m["chosen_inline_result"]),
        callbackQuery = callbackQueryFromMap(m["callback_query"]),
        shippingQuery = shippingQueryFromMap(m["shipping_query"]),
        preCheckoutQuery = preCheckoutQueryFromMap(m["pre_checkout_query"]),
        purchasedPaidMedia = paidMediaPurchasedFromMap(m["purchased_paid_media"]),
        poll = pollFromMap(m["poll"]),
        pollAnswer = pollAnswerFromMap(m["poll_answer"]),
        myChatMember = chatMemberUpdatedFromMap(m["my_chat_member"]),
        chatMember = chatMemberUpdatedFromMap(m["chat_member"]),
        chatJoinRequest = chatJoinRequestFromMap(m["chat_join_request"]),
        chatBoost = chatBoostUpdatedFromMap(m["chat_boost"]),
        removedChatBoost = chatBoostRemovedFromMap(m["removed_chat_boost"]),
        managedBot = managedBotUpdatedFromMap(m["managed_bot"]),
        subscription = botSubscriptionUpdatedFromMap(m["subscription"]),
        stoppedMessageGeneration = messageGenerationStoppedFromMap(m["stopped_message_generation"]),
    )
}

fun userFromMap(raw: Any?): User? {
    val m = raw as? Map<*, *> ?: return null
    return User(
        id = botLong(m["id"]) ?: 0,
        isBot = botBool(m["is_bot"]) ?: false,
        firstName = botString(m["first_name"]) ?: "",
        lastName = botString(m["last_name"]),
        username = botString(m["username"]),
        languageCode = botString(m["language_code"]),
        isPremium = botBool(m["is_premium"]),
        addedToAttachmentMenu = botBool(m["added_to_attachment_menu"]),
        canJoinGroups = botBool(m["can_join_groups"]),
        canReadAllGroupMessages = botBool(m["can_read_all_group_messages"]),
        supportsGuestQueries = botBool(m["supports_guest_queries"]),
        supportsInlineQueries = botBool(m["supports_inline_queries"]),
        canConnectToBusiness = botBool(m["can_connect_to_business"]),
        hasMainWebApp = botBool(m["has_main_web_app"]),
        hasTopicsEnabled = botBool(m["has_topics_enabled"]),
        allowsUsersToCreateTopics = botBool(m["allows_users_to_create_topics"]),
        canManageBots = botBool(m["can_manage_bots"]),
        supportsJoinRequestQueries = botBool(m["supports_join_request_queries"]),
    )
}

fun userChatBoostsFromMap(raw: Any?): UserChatBoosts? {
    val m = raw as? Map<*, *> ?: return null
    return UserChatBoosts(
        boosts = botList(m["boosts"]) { chatBoostFromMap(it) } ?: emptyList(),
    )
}

fun userProfileAudiosFromMap(raw: Any?): UserProfileAudios? {
    val m = raw as? Map<*, *> ?: return null
    return UserProfileAudios(
        totalCount = botLong(m["total_count"]) ?: 0,
        audios = botList(m["audios"]) { audioFromMap(it) } ?: emptyList(),
    )
}

fun userProfilePhotosFromMap(raw: Any?): UserProfilePhotos? {
    val m = raw as? Map<*, *> ?: return null
    return UserProfilePhotos(
        totalCount = botLong(m["total_count"]) ?: 0,
        photos = botList(m["photos"]) { botList(it) { photoSizeFromMap(it) } } ?: emptyList(),
    )
}

fun userRatingFromMap(raw: Any?): UserRating? {
    val m = raw as? Map<*, *> ?: return null
    return UserRating(
        level = botLong(m["level"]) ?: 0,
        rating = botLong(m["rating"]) ?: 0,
        currentLevelRating = botLong(m["current_level_rating"]) ?: 0,
        nextLevelRating = botLong(m["next_level_rating"]),
    )
}

fun usersSharedFromMap(raw: Any?): UsersShared? {
    val m = raw as? Map<*, *> ?: return null
    return UsersShared(
        requestId = botLong(m["request_id"]) ?: 0,
        users = botList(m["users"]) { sharedUserFromMap(it) } ?: emptyList(),
    )
}

fun venueFromMap(raw: Any?): Venue? {
    val m = raw as? Map<*, *> ?: return null
    return Venue(
        location = locationFromMap(m["location"]),
        title = botString(m["title"]) ?: "",
        address = botString(m["address"]) ?: "",
        foursquareId = botString(m["foursquare_id"]),
        foursquareType = botString(m["foursquare_type"]),
        googlePlaceId = botString(m["google_place_id"]),
        googlePlaceType = botString(m["google_place_type"]),
    )
}

fun videoFromMap(raw: Any?): Video? {
    val m = raw as? Map<*, *> ?: return null
    return Video(
        fileId = botString(m["file_id"]) ?: "",
        fileUniqueId = botString(m["file_unique_id"]) ?: "",
        width = botLong(m["width"]) ?: 0,
        height = botLong(m["height"]) ?: 0,
        duration = botLong(m["duration"]) ?: 0,
        thumbnail = photoSizeFromMap(m["thumbnail"]),
        cover = botList(m["cover"]) { photoSizeFromMap(it) },
        startTimestamp = botLong(m["start_timestamp"]),
        qualities = botList(m["qualities"]) { videoQualityFromMap(it) },
        fileName = botString(m["file_name"]),
        mimeType = botString(m["mime_type"]),
        fileSize = botLong(m["file_size"]),
    )
}

fun videoChatEndedFromMap(raw: Any?): VideoChatEnded? {
    val m = raw as? Map<*, *> ?: return null
    return VideoChatEnded(
        duration = botLong(m["duration"]) ?: 0,
    )
}

fun videoChatParticipantsInvitedFromMap(raw: Any?): VideoChatParticipantsInvited? {
    val m = raw as? Map<*, *> ?: return null
    return VideoChatParticipantsInvited(
        users = botList(m["users"]) { userFromMap(it) } ?: emptyList(),
    )
}

fun videoChatScheduledFromMap(raw: Any?): VideoChatScheduled? {
    val m = raw as? Map<*, *> ?: return null
    return VideoChatScheduled(
        startDate = botLong(m["start_date"]) ?: 0,
    )
}

fun videoChatStartedFromMap(raw: Any?): VideoChatStarted? =
    if (raw is Map<*, *> || raw == true) VideoChatStarted else null

fun videoNoteFromMap(raw: Any?): VideoNote? {
    val m = raw as? Map<*, *> ?: return null
    return VideoNote(
        fileId = botString(m["file_id"]) ?: "",
        fileUniqueId = botString(m["file_unique_id"]) ?: "",
        length = botLong(m["length"]) ?: 0,
        duration = botLong(m["duration"]) ?: 0,
        thumbnail = photoSizeFromMap(m["thumbnail"]),
        fileSize = botLong(m["file_size"]),
    )
}

fun videoQualityFromMap(raw: Any?): VideoQuality? {
    val m = raw as? Map<*, *> ?: return null
    return VideoQuality(
        fileId = botString(m["file_id"]) ?: "",
        fileUniqueId = botString(m["file_unique_id"]) ?: "",
        width = botLong(m["width"]) ?: 0,
        height = botLong(m["height"]) ?: 0,
        codec = botString(m["codec"]) ?: "",
        fileSize = botLong(m["file_size"]),
    )
}

fun voiceFromMap(raw: Any?): Voice? {
    val m = raw as? Map<*, *> ?: return null
    return Voice(
        fileId = botString(m["file_id"]) ?: "",
        fileUniqueId = botString(m["file_unique_id"]) ?: "",
        duration = botLong(m["duration"]) ?: 0,
        mimeType = botString(m["mime_type"]),
        fileSize = botLong(m["file_size"]),
    )
}

fun webAppDataFromMap(raw: Any?): WebAppData? {
    val m = raw as? Map<*, *> ?: return null
    return WebAppData(
        data = botString(m["data"]) ?: "",
        buttonText = botString(m["button_text"]) ?: "",
    )
}

fun webAppInfoFromMap(raw: Any?): WebAppInfo? {
    val m = raw as? Map<*, *> ?: return null
    return WebAppInfo(
        url = botString(m["url"]) ?: "",
    )
}

fun webhookInfoFromMap(raw: Any?): WebhookInfo? {
    val m = raw as? Map<*, *> ?: return null
    return WebhookInfo(
        url = botString(m["url"]) ?: "",
        hasCustomCertificate = botBool(m["has_custom_certificate"]) ?: false,
        pendingUpdateCount = botLong(m["pending_update_count"]) ?: 0,
        ipAddress = botString(m["ip_address"]),
        lastErrorDate = botLong(m["last_error_date"]),
        lastErrorMessage = botString(m["last_error_message"]),
        lastSynchronizationErrorDate = botLong(m["last_synchronization_error_date"]),
        maxConnections = botLong(m["max_connections"]),
        allowedUpdates = botList(m["allowed_updates"]) { botString(it) },
    )
}

fun writeAccessAllowedFromMap(raw: Any?): WriteAccessAllowed? {
    val m = raw as? Map<*, *> ?: return null
    return WriteAccessAllowed(
        fromRequest = botBool(m["from_request"]),
        webAppName = botString(m["web_app_name"]),
        fromAttachmentMenu = botBool(m["from_attachment_menu"]),
    )
}

fun mediaOfInputRichMessageMediaFromMap(raw: Any?): MediaOfInputRichMessageMedia? {
    val m = raw as? Map<*, *> ?: return null
    return listOf(
        BotAlt(setOf("type", "media", "thumbnail", "caption", "parse_mode", "caption_entities", "show_caption_above_media", "width", "height", "duration", "has_spoiler")) { inputMediaAnimationFromMap(it) },
        BotAlt(setOf("type", "media", "thumbnail", "caption", "parse_mode", "caption_entities", "duration", "performer", "title")) { inputMediaAudioFromMap(it) },
        BotAlt(setOf("type", "media", "thumbnail", "caption", "parse_mode", "caption_entities", "disable_content_type_detection")) { inputMediaDocumentFromMap(it) },
        BotAlt(setOf("type", "media", "caption", "parse_mode", "caption_entities", "show_caption_above_media", "has_spoiler")) { inputMediaPhotoFromMap(it) },
        BotAlt(setOf("type", "media", "thumbnail", "cover", "start_timestamp", "caption", "parse_mode", "caption_entities", "show_caption_above_media", "width", "height", "duration", "supports_streaming", "has_spoiler")) { inputMediaVideoFromMap(it) },
        BotAlt(setOf("type", "media", "caption", "parse_mode", "caption_entities", "duration")) { inputMediaVoiceNoteFromMap(it) },
    ).bestMatch(m)
}

fun backgroundFillFromMap(raw: Any?): BackgroundFill? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["type"] as? String) {
        "solid" -> backgroundFillSolidFromMap(raw)
        "gradient" -> backgroundFillGradientFromMap(raw)
        "freeform_gradient" -> backgroundFillFreeformGradientFromMap(raw)
        else -> null
    }
}

fun backgroundTypeFromMap(raw: Any?): BackgroundType? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["type"] as? String) {
        "fill" -> backgroundTypeFillFromMap(raw)
        "wallpaper" -> backgroundTypeWallpaperFromMap(raw)
        "pattern" -> backgroundTypePatternFromMap(raw)
        "chat_theme" -> backgroundTypeChatThemeFromMap(raw)
        else -> null
    }
}

fun botCommandScopeFromMap(raw: Any?): BotCommandScope? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["type"] as? String) {
        "default" -> botCommandScopeDefaultFromMap(raw)
        "all_private_chats" -> botCommandScopeAllPrivateChatsFromMap(raw)
        "all_group_chats" -> botCommandScopeAllGroupChatsFromMap(raw)
        "all_chat_administrators" -> botCommandScopeAllChatAdministratorsFromMap(raw)
        "chat" -> botCommandScopeChatFromMap(raw)
        "chat_administrators" -> botCommandScopeChatAdministratorsFromMap(raw)
        "chat_member" -> botCommandScopeChatMemberFromMap(raw)
        else -> null
    }
}

fun chatBoostSourceFromMap(raw: Any?): ChatBoostSource? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["source"] as? String) {
        "premium" -> chatBoostSourcePremiumFromMap(raw)
        "gift_code" -> chatBoostSourceGiftCodeFromMap(raw)
        "giveaway" -> chatBoostSourceGiveawayFromMap(raw)
        else -> null
    }
}

fun chatMemberFromMap(raw: Any?): ChatMember? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["status"] as? String) {
        "creator" -> chatMemberOwnerFromMap(raw)
        "administrator" -> chatMemberAdministratorFromMap(raw)
        "member" -> chatMemberMemberFromMap(raw)
        "restricted" -> chatMemberRestrictedFromMap(raw)
        "left" -> chatMemberLeftFromMap(raw)
        "kicked" -> chatMemberBannedFromMap(raw)
        else -> null
    }
}

fun inlineQueryResultFromMap(raw: Any?): InlineQueryResult? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["type"] as? String) {
        "audio" -> listOf(
            BotAlt(setOf("type", "id", "audio_file_id", "caption", "parse_mode", "caption_entities", "reply_markup", "input_message_content")) { inlineQueryResultCachedAudioFromMap(it) },
            BotAlt(setOf("type", "id", "audio_url", "title", "caption", "parse_mode", "caption_entities", "performer", "audio_duration", "reply_markup", "input_message_content")) { inlineQueryResultAudioFromMap(it) },
        ).bestMatch(m)
        "document" -> listOf(
            BotAlt(setOf("type", "id", "title", "document_file_id", "description", "caption", "parse_mode", "caption_entities", "reply_markup", "input_message_content")) { inlineQueryResultCachedDocumentFromMap(it) },
            BotAlt(setOf("type", "id", "title", "caption", "parse_mode", "caption_entities", "document_url", "mime_type", "description", "reply_markup", "input_message_content", "thumbnail_url", "thumbnail_width", "thumbnail_height")) { inlineQueryResultDocumentFromMap(it) },
        ).bestMatch(m)
        "gif" -> listOf(
            BotAlt(setOf("type", "id", "gif_file_id", "title", "caption", "parse_mode", "caption_entities", "show_caption_above_media", "reply_markup", "input_message_content")) { inlineQueryResultCachedGifFromMap(it) },
            BotAlt(setOf("type", "id", "gif_url", "gif_width", "gif_height", "gif_duration", "thumbnail_url", "thumbnail_mime_type", "title", "caption", "parse_mode", "caption_entities", "show_caption_above_media", "reply_markup", "input_message_content")) { inlineQueryResultGifFromMap(it) },
        ).bestMatch(m)
        "mpeg4_gif" -> listOf(
            BotAlt(setOf("type", "id", "mpeg4_file_id", "title", "caption", "parse_mode", "caption_entities", "show_caption_above_media", "reply_markup", "input_message_content")) { inlineQueryResultCachedMpeg4GifFromMap(it) },
            BotAlt(setOf("type", "id", "mpeg4_url", "mpeg4_width", "mpeg4_height", "mpeg4_duration", "thumbnail_url", "thumbnail_mime_type", "title", "caption", "parse_mode", "caption_entities", "show_caption_above_media", "reply_markup", "input_message_content")) { inlineQueryResultMpeg4GifFromMap(it) },
        ).bestMatch(m)
        "photo" -> listOf(
            BotAlt(setOf("type", "id", "photo_file_id", "title", "description", "caption", "parse_mode", "caption_entities", "show_caption_above_media", "reply_markup", "input_message_content")) { inlineQueryResultCachedPhotoFromMap(it) },
            BotAlt(setOf("type", "id", "photo_url", "thumbnail_url", "photo_width", "photo_height", "title", "description", "caption", "parse_mode", "caption_entities", "show_caption_above_media", "reply_markup", "input_message_content")) { inlineQueryResultPhotoFromMap(it) },
        ).bestMatch(m)
        "sticker" -> inlineQueryResultCachedStickerFromMap(raw)
        "video" -> listOf(
            BotAlt(setOf("type", "id", "video_file_id", "title", "description", "caption", "parse_mode", "caption_entities", "show_caption_above_media", "reply_markup", "input_message_content")) { inlineQueryResultCachedVideoFromMap(it) },
            BotAlt(setOf("type", "id", "video_url", "mime_type", "thumbnail_url", "title", "caption", "parse_mode", "caption_entities", "show_caption_above_media", "video_width", "video_height", "video_duration", "description", "reply_markup", "input_message_content")) { inlineQueryResultVideoFromMap(it) },
        ).bestMatch(m)
        "voice" -> listOf(
            BotAlt(setOf("type", "id", "voice_file_id", "title", "caption", "parse_mode", "caption_entities", "reply_markup", "input_message_content")) { inlineQueryResultCachedVoiceFromMap(it) },
            BotAlt(setOf("type", "id", "voice_url", "title", "caption", "parse_mode", "caption_entities", "voice_duration", "reply_markup", "input_message_content")) { inlineQueryResultVoiceFromMap(it) },
        ).bestMatch(m)
        "article" -> inlineQueryResultArticleFromMap(raw)
        "contact" -> inlineQueryResultContactFromMap(raw)
        "game" -> inlineQueryResultGameFromMap(raw)
        "location" -> inlineQueryResultLocationFromMap(raw)
        "venue" -> inlineQueryResultVenueFromMap(raw)
        else -> null
    }
}

fun inputMediaFromMap(raw: Any?): InputMedia? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["type"] as? String) {
        "animation" -> inputMediaAnimationFromMap(raw)
        "audio" -> inputMediaAudioFromMap(raw)
        "document" -> inputMediaDocumentFromMap(raw)
        "live_photo" -> inputMediaLivePhotoFromMap(raw)
        "photo" -> inputMediaPhotoFromMap(raw)
        "video" -> inputMediaVideoFromMap(raw)
        else -> null
    }
}

fun inputMessageContentFromMap(raw: Any?): InputMessageContent? {
    val m = raw as? Map<*, *> ?: return null
    return listOf(
        BotAlt(setOf("message_text", "parse_mode", "entities", "link_preview_options")) { inputTextMessageContentFromMap(it) },
        BotAlt(setOf("rich_message")) { inputRichMessageContentFromMap(it) },
        BotAlt(setOf("latitude", "longitude", "horizontal_accuracy", "live_period", "heading", "proximity_alert_radius")) { inputLocationMessageContentFromMap(it) },
        BotAlt(setOf("latitude", "longitude", "title", "address", "foursquare_id", "foursquare_type", "google_place_id", "google_place_type")) { inputVenueMessageContentFromMap(it) },
        BotAlt(setOf("phone_number", "first_name", "last_name", "vcard")) { inputContactMessageContentFromMap(it) },
        BotAlt(setOf("title", "description", "payload", "provider_token", "currency", "prices", "max_tip_amount", "suggested_tip_amounts", "provider_data", "photo_url", "photo_size", "photo_width", "photo_height", "need_name", "need_phone_number", "need_email", "need_shipping_address", "send_phone_number_to_provider", "send_email_to_provider", "is_flexible")) { inputInvoiceMessageContentFromMap(it) },
    ).bestMatch(m)
}

fun inputPaidMediaFromMap(raw: Any?): InputPaidMedia? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["type"] as? String) {
        "live_photo" -> inputPaidMediaLivePhotoFromMap(raw)
        "photo" -> inputPaidMediaPhotoFromMap(raw)
        "video" -> inputPaidMediaVideoFromMap(raw)
        else -> null
    }
}

fun inputPollMediaFromMap(raw: Any?): InputPollMedia? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["type"] as? String) {
        "animation" -> inputMediaAnimationFromMap(raw)
        "audio" -> inputMediaAudioFromMap(raw)
        "document" -> inputMediaDocumentFromMap(raw)
        "live_photo" -> inputMediaLivePhotoFromMap(raw)
        "location" -> inputMediaLocationFromMap(raw)
        "photo" -> inputMediaPhotoFromMap(raw)
        "venue" -> inputMediaVenueFromMap(raw)
        "video" -> inputMediaVideoFromMap(raw)
        else -> null
    }
}

fun inputPollOptionMediaFromMap(raw: Any?): InputPollOptionMedia? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["type"] as? String) {
        "animation" -> inputMediaAnimationFromMap(raw)
        "link" -> inputMediaLinkFromMap(raw)
        "live_photo" -> inputMediaLivePhotoFromMap(raw)
        "location" -> inputMediaLocationFromMap(raw)
        "photo" -> inputMediaPhotoFromMap(raw)
        "sticker" -> inputMediaStickerFromMap(raw)
        "venue" -> inputMediaVenueFromMap(raw)
        "video" -> inputMediaVideoFromMap(raw)
        else -> null
    }
}

fun inputProfilePhotoFromMap(raw: Any?): InputProfilePhoto? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["type"] as? String) {
        "static" -> inputProfilePhotoStaticFromMap(raw)
        "animated" -> inputProfilePhotoAnimatedFromMap(raw)
        else -> null
    }
}

fun inputRichBlockFromMap(raw: Any?): InputRichBlock? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["type"] as? String) {
        "paragraph" -> inputRichBlockParagraphFromMap(raw)
        "heading" -> inputRichBlockSectionHeadingFromMap(raw)
        "pre" -> inputRichBlockPreformattedFromMap(raw)
        "footer" -> inputRichBlockFooterFromMap(raw)
        "divider" -> inputRichBlockDividerFromMap(raw)
        "mathematical_expression" -> inputRichBlockMathematicalExpressionFromMap(raw)
        "anchor" -> inputRichBlockAnchorFromMap(raw)
        "list" -> inputRichBlockListFromMap(raw)
        "blockquote" -> inputRichBlockBlockQuotationFromMap(raw)
        "expandable_blockquote" -> inputRichBlockExpandableBlockQuotationFromMap(raw)
        "pullquote" -> inputRichBlockPullQuotationFromMap(raw)
        "collage" -> inputRichBlockCollageFromMap(raw)
        "slideshow" -> inputRichBlockSlideshowFromMap(raw)
        "table" -> inputRichBlockTableFromMap(raw)
        "details" -> inputRichBlockDetailsFromMap(raw)
        "map" -> inputRichBlockMapFromMap(raw)
        "buttons" -> inputRichBlockButtonsFromMap(raw)
        "animation" -> inputRichBlockAnimationFromMap(raw)
        "audio" -> inputRichBlockAudioFromMap(raw)
        "document" -> inputRichBlockDocumentFromMap(raw)
        "photo" -> inputRichBlockPhotoFromMap(raw)
        "video" -> inputRichBlockVideoFromMap(raw)
        "voice_note" -> inputRichBlockVoiceNoteFromMap(raw)
        "thinking" -> inputRichBlockThinkingFromMap(raw)
        else -> null
    }
}

fun inputStoryContentFromMap(raw: Any?): InputStoryContent? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["type"] as? String) {
        "photo" -> inputStoryContentPhotoFromMap(raw)
        "video" -> inputStoryContentVideoFromMap(raw)
        else -> null
    }
}

fun maybeInaccessibleMessageFromMap(raw: Any?): MaybeInaccessibleMessage? {
    val m = raw as? Map<*, *> ?: return null
    return listOf(
        BotAlt(setOf("message_id", "message_thread_id", "direct_messages_topic", "from", "sender_chat", "sender_boost_count", "sender_business_bot", "sender_tag", "receiver_user", "ephemeral_message_id", "date", "guest_query_id", "business_connection_id", "chat", "forward_origin", "is_topic_message", "is_automatic_forward", "reply_to_message", "external_reply", "quote", "reply_to_story", "reply_to_checklist_task_id", "reply_to_poll_option_id", "via_bot", "guest_bot_caller_user", "guest_bot_caller_chat", "edit_date", "has_protected_content", "is_from_offline", "is_paid_post", "media_group_id", "author_signature", "paid_star_count", "text", "entities", "link_preview_options", "suggested_post_info", "effect_id", "rich_message", "animation", "audio", "document", "live_photo", "paid_media", "photo", "sticker", "story", "video", "video_note", "voice", "caption", "caption_entities", "show_caption_above_media", "has_media_spoiler", "checklist", "contact", "dice", "game", "poll", "venue", "location", "new_chat_members", "left_chat_member", "chat_owner_left", "chat_owner_changed", "new_chat_title", "new_chat_photo", "delete_chat_photo", "group_chat_created", "supergroup_chat_created", "channel_chat_created", "message_auto_delete_timer_changed", "migrate_to_chat_id", "migrate_from_chat_id", "pinned_message", "invoice", "successful_payment", "refunded_payment", "users_shared", "chat_shared", "gift", "unique_gift", "gift_upgrade_sent", "connected_website", "write_access_allowed", "passport_data", "proximity_alert_triggered", "boost_added", "chat_background_set", "checklist_tasks_done", "checklist_tasks_added", "community_chat_added", "community_chat_joined", "community_chat_removed", "direct_message_price_changed", "forum_topic_created", "forum_topic_edited", "forum_topic_closed", "forum_topic_reopened", "general_forum_topic_hidden", "general_forum_topic_unhidden", "giveaway_created", "giveaway", "giveaway_winners", "giveaway_completed", "managed_bot_created", "paid_message_price_changed", "poll_option_added", "poll_option_deleted", "suggested_post_approved", "suggested_post_approval_failed", "suggested_post_declined", "suggested_post_paid", "suggested_post_refunded", "video_chat_scheduled", "video_chat_started", "video_chat_ended", "video_chat_participants_invited", "web_app_data", "reply_markup")) { messageFromMap(it) },
        BotAlt(setOf("chat", "message_id", "date")) { inaccessibleMessageFromMap(it) },
    ).bestMatch(m)
}

fun menuButtonFromMap(raw: Any?): MenuButton? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["type"] as? String) {
        "commands" -> menuButtonCommandsFromMap(raw)
        "web_app" -> menuButtonWebAppFromMap(raw)
        "default" -> menuButtonDefaultFromMap(raw)
        else -> null
    }
}

fun messageOriginFromMap(raw: Any?): MessageOrigin? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["type"] as? String) {
        "user" -> messageOriginUserFromMap(raw)
        "hidden_user" -> messageOriginHiddenUserFromMap(raw)
        "chat" -> messageOriginChatFromMap(raw)
        "channel" -> messageOriginChannelFromMap(raw)
        else -> null
    }
}

fun ownedGiftFromMap(raw: Any?): OwnedGift? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["type"] as? String) {
        "regular" -> ownedGiftRegularFromMap(raw)
        "unique" -> ownedGiftUniqueFromMap(raw)
        else -> null
    }
}

fun paidMediaFromMap(raw: Any?): PaidMedia? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["type"] as? String) {
        "live_photo" -> paidMediaLivePhotoFromMap(raw)
        "photo" -> paidMediaPhotoFromMap(raw)
        "preview" -> paidMediaPreviewFromMap(raw)
        "video" -> paidMediaVideoFromMap(raw)
        else -> null
    }
}

fun passportElementErrorFromMap(raw: Any?): PassportElementError? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["source"] as? String) {
        "data" -> passportElementErrorDataFieldFromMap(raw)
        "front_side" -> passportElementErrorFrontSideFromMap(raw)
        "reverse_side" -> passportElementErrorReverseSideFromMap(raw)
        "selfie" -> passportElementErrorSelfieFromMap(raw)
        "file" -> passportElementErrorFileFromMap(raw)
        "files" -> passportElementErrorFilesFromMap(raw)
        "translation_file" -> passportElementErrorTranslationFileFromMap(raw)
        "translation_files" -> passportElementErrorTranslationFilesFromMap(raw)
        "unspecified" -> passportElementErrorUnspecifiedFromMap(raw)
        else -> null
    }
}

fun reactionTypeFromMap(raw: Any?): ReactionType? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["type"] as? String) {
        "emoji" -> reactionTypeEmojiFromMap(raw)
        "custom_emoji" -> reactionTypeCustomEmojiFromMap(raw)
        "paid" -> reactionTypePaidFromMap(raw)
        else -> null
    }
}

fun revenueWithdrawalStateFromMap(raw: Any?): RevenueWithdrawalState? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["type"] as? String) {
        "pending" -> revenueWithdrawalStatePendingFromMap(raw)
        "succeeded" -> revenueWithdrawalStateSucceededFromMap(raw)
        "failed" -> revenueWithdrawalStateFailedFromMap(raw)
        else -> null
    }
}

fun richBlockFromMap(raw: Any?): RichBlock? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["type"] as? String) {
        "paragraph" -> richBlockParagraphFromMap(raw)
        "heading" -> richBlockSectionHeadingFromMap(raw)
        "pre" -> richBlockPreformattedFromMap(raw)
        "footer" -> richBlockFooterFromMap(raw)
        "divider" -> richBlockDividerFromMap(raw)
        "mathematical_expression" -> richBlockMathematicalExpressionFromMap(raw)
        "anchor" -> richBlockAnchorFromMap(raw)
        "list" -> richBlockListFromMap(raw)
        "blockquote" -> richBlockBlockQuotationFromMap(raw)
        "expandable_blockquote" -> richBlockExpandableBlockQuotationFromMap(raw)
        "pullquote" -> richBlockPullQuotationFromMap(raw)
        "collage" -> richBlockCollageFromMap(raw)
        "slideshow" -> richBlockSlideshowFromMap(raw)
        "table" -> richBlockTableFromMap(raw)
        "details" -> richBlockDetailsFromMap(raw)
        "map" -> richBlockMapFromMap(raw)
        "buttons" -> richBlockButtonsFromMap(raw)
        "animation" -> richBlockAnimationFromMap(raw)
        "audio" -> richBlockAudioFromMap(raw)
        "document" -> richBlockDocumentFromMap(raw)
        "photo" -> richBlockPhotoFromMap(raw)
        "video" -> richBlockVideoFromMap(raw)
        "voice_note" -> richBlockVoiceNoteFromMap(raw)
        "thinking" -> richBlockThinkingFromMap(raw)
        else -> null
    }
}

fun richTextFromMap(raw: Any?): RichText? = when (raw) {
    is String -> RichTextPlain(raw)
    is List<*> -> RichTextParts(raw.mapNotNull { richTextFromMap(it) })
    is Map<*, *> -> when (raw["type"] as? String) {
        "bold" -> richTextBoldFromMap(raw)
        "italic" -> richTextItalicFromMap(raw)
        "underline" -> richTextUnderlineFromMap(raw)
        "strikethrough" -> richTextStrikethroughFromMap(raw)
        "spoiler" -> richTextSpoilerFromMap(raw)
        "date_time" -> richTextDateTimeFromMap(raw)
        "text_mention" -> richTextTextMentionFromMap(raw)
        "subscript" -> richTextSubscriptFromMap(raw)
        "superscript" -> richTextSuperscriptFromMap(raw)
        "marked" -> richTextMarkedFromMap(raw)
        "code" -> richTextCodeFromMap(raw)
        "custom_emoji" -> richTextCustomEmojiFromMap(raw)
        "mathematical_expression" -> richTextMathematicalExpressionFromMap(raw)
        "url" -> richTextUrlFromMap(raw)
        "email_address" -> richTextEmailAddressFromMap(raw)
        "phone_number" -> richTextPhoneNumberFromMap(raw)
        "bank_card_number" -> richTextBankCardNumberFromMap(raw)
        "mention" -> richTextMentionFromMap(raw)
        "hashtag" -> richTextHashtagFromMap(raw)
        "cashtag" -> richTextCashtagFromMap(raw)
        "bot_command" -> richTextBotCommandFromMap(raw)
        "button" -> richTextButtonFromMap(raw)
        "anchor" -> richTextAnchorFromMap(raw)
        "anchor_link" -> richTextAnchorLinkFromMap(raw)
        "reference" -> richTextReferenceFromMap(raw)
        "reference_link" -> richTextReferenceLinkFromMap(raw)
        else -> null
    }
    else -> null
}

fun storyAreaTypeFromMap(raw: Any?): StoryAreaType? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["type"] as? String) {
        "location" -> storyAreaTypeLocationFromMap(raw)
        "suggested_reaction" -> storyAreaTypeSuggestedReactionFromMap(raw)
        "link" -> storyAreaTypeLinkFromMap(raw)
        "weather" -> storyAreaTypeWeatherFromMap(raw)
        "unique_gift" -> storyAreaTypeUniqueGiftFromMap(raw)
        else -> null
    }
}

fun transactionPartnerFromMap(raw: Any?): TransactionPartner? {
    val m = raw as? Map<*, *> ?: return null
    return when (m["type"] as? String) {
        "user" -> transactionPartnerUserFromMap(raw)
        "chat" -> transactionPartnerChatFromMap(raw)
        "affiliate_program" -> transactionPartnerAffiliateProgramFromMap(raw)
        "fragment" -> transactionPartnerFragmentFromMap(raw)
        "telegram_ads" -> transactionPartnerTelegramAdsFromMap(raw)
        "telegram_api" -> transactionPartnerTelegramApiFromMap(raw)
        "other" -> transactionPartnerOtherFromMap(raw)
        else -> null
    }
}

