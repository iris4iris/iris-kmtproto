// Generated from Bot API 10.3 (August 24, 2026). Do not edit.
// Regenerate: python3 gen/generate_bot_api.py
package iris.kmtproto.bot

sealed interface MediaOfInputRichMessageMedia

/** [BackgroundFill](https://core.telegram.org/bots/api#backgroundfill). */
sealed interface BackgroundFill

/** [BackgroundType](https://core.telegram.org/bots/api#backgroundtype). */
sealed interface BackgroundType

/** [BotCommandScope](https://core.telegram.org/bots/api#botcommandscope). */
sealed interface BotCommandScope

/** [ChatBoostSource](https://core.telegram.org/bots/api#chatboostsource). */
sealed interface ChatBoostSource

/** [ChatMember](https://core.telegram.org/bots/api#chatmember). */
sealed interface ChatMember

/** [InlineQueryResult](https://core.telegram.org/bots/api#inlinequeryresult). */
sealed interface InlineQueryResult

/** [InputMedia](https://core.telegram.org/bots/api#inputmedia). */
sealed interface InputMedia

/** [InputMessageContent](https://core.telegram.org/bots/api#inputmessagecontent). */
sealed interface InputMessageContent

/** [InputPaidMedia](https://core.telegram.org/bots/api#inputpaidmedia). */
sealed interface InputPaidMedia

/** [InputPollMedia](https://core.telegram.org/bots/api#inputpollmedia). */
sealed interface InputPollMedia

/** [InputPollOptionMedia](https://core.telegram.org/bots/api#inputpolloptionmedia). */
sealed interface InputPollOptionMedia

/** [InputProfilePhoto](https://core.telegram.org/bots/api#inputprofilephoto). */
sealed interface InputProfilePhoto

/** [InputRichBlock](https://core.telegram.org/bots/api#inputrichblock). */
sealed interface InputRichBlock

/** [InputStoryContent](https://core.telegram.org/bots/api#inputstorycontent). */
sealed interface InputStoryContent

/** [MaybeInaccessibleMessage](https://core.telegram.org/bots/api#maybeinaccessiblemessage). */
sealed interface MaybeInaccessibleMessage

/** [MenuButton](https://core.telegram.org/bots/api#menubutton). */
sealed interface MenuButton

/** [MessageOrigin](https://core.telegram.org/bots/api#messageorigin). */
sealed interface MessageOrigin

/** [OwnedGift](https://core.telegram.org/bots/api#ownedgift). */
sealed interface OwnedGift

/** [PaidMedia](https://core.telegram.org/bots/api#paidmedia). */
sealed interface PaidMedia

/** [PassportElementError](https://core.telegram.org/bots/api#passportelementerror). */
sealed interface PassportElementError

/** [ReactionType](https://core.telegram.org/bots/api#reactiontype). */
sealed interface ReactionType

/** [RevenueWithdrawalState](https://core.telegram.org/bots/api#revenuewithdrawalstate). */
sealed interface RevenueWithdrawalState

/** [RichBlock](https://core.telegram.org/bots/api#richblock). */
sealed interface RichBlock

/** [RichText](https://core.telegram.org/bots/api#richtext). Non-object values: String, Array of RichText. */
sealed interface RichText

/** [StoryAreaType](https://core.telegram.org/bots/api#storyareatype). */
sealed interface StoryAreaType

/** [TransactionPartner](https://core.telegram.org/bots/api#transactionpartner). */
sealed interface TransactionPartner

/** [AcceptedGiftTypes](https://core.telegram.org/bots/api#acceptedgifttypes). */
data class AcceptedGiftTypes(
    val unlimitedGifts: Boolean = false,
    val limitedGifts: Boolean = false,
    val uniqueGifts: Boolean = false,
    val premiumSubscription: Boolean = false,
    val giftsFromChannels: Boolean = false,
)

/** [AffiliateInfo](https://core.telegram.org/bots/api#affiliateinfo). */
data class AffiliateInfo(
    val affiliateUser: User? = null,
    val affiliateChat: Chat? = null,
    val commissionPerMille: Long = 0,
    val amount: Long = 0,
    val nanostarAmount: Long? = null,
)

/** [Animation](https://core.telegram.org/bots/api#animation). */
data class Animation(
    val fileId: String = "",
    val fileUniqueId: String = "",
    val width: Long = 0,
    val height: Long = 0,
    val duration: Long = 0,
    val thumbnail: PhotoSize? = null,
    val fileName: String? = null,
    val mimeType: String? = null,
    val fileSize: Long? = null,
)

/** [Audio](https://core.telegram.org/bots/api#audio). */
data class Audio(
    val fileId: String = "",
    val fileUniqueId: String = "",
    val duration: Long = 0,
    val performer: String? = null,
    val title: String? = null,
    val fileName: String? = null,
    val mimeType: String? = null,
    val fileSize: Long? = null,
    val thumbnail: PhotoSize? = null,
)

/** [BackgroundFillFreeformGradient](https://core.telegram.org/bots/api#backgroundfillfreeformgradient). */
data class BackgroundFillFreeformGradient(
    val type: String = "",
    val colors: List<Long> = emptyList(),
) : BackgroundFill

/** [BackgroundFillGradient](https://core.telegram.org/bots/api#backgroundfillgradient). */
data class BackgroundFillGradient(
    val type: String = "",
    val topColor: Long = 0,
    val bottomColor: Long = 0,
    val rotationAngle: Long = 0,
) : BackgroundFill

/** [BackgroundFillSolid](https://core.telegram.org/bots/api#backgroundfillsolid). */
data class BackgroundFillSolid(
    val type: String = "",
    val color: Long = 0,
) : BackgroundFill

/** [BackgroundTypeChatTheme](https://core.telegram.org/bots/api#backgroundtypechattheme). */
data class BackgroundTypeChatTheme(
    val type: String = "",
    val themeName: String = "",
) : BackgroundType

/** [BackgroundTypeFill](https://core.telegram.org/bots/api#backgroundtypefill). */
data class BackgroundTypeFill(
    val type: String = "",
    val fill: BackgroundFill? = null,
    val darkThemeDimming: Long = 0,
) : BackgroundType

/** [BackgroundTypePattern](https://core.telegram.org/bots/api#backgroundtypepattern). */
data class BackgroundTypePattern(
    val type: String = "",
    val document: Document? = null,
    val fill: BackgroundFill? = null,
    val intensity: Long = 0,
    val isInverted: Boolean? = null,
    val isMoving: Boolean? = null,
) : BackgroundType

/** [BackgroundTypeWallpaper](https://core.telegram.org/bots/api#backgroundtypewallpaper). */
data class BackgroundTypeWallpaper(
    val type: String = "",
    val document: Document? = null,
    val darkThemeDimming: Long = 0,
    val isBlurred: Boolean? = null,
    val isMoving: Boolean? = null,
) : BackgroundType

/** [Birthdate](https://core.telegram.org/bots/api#birthdate). */
data class Birthdate(
    val day: Long = 0,
    val month: Long = 0,
    val year: Long? = null,
)

/** [BotAccessSettings](https://core.telegram.org/bots/api#botaccesssettings). */
data class BotAccessSettings(
    val isAccessRestricted: Boolean = false,
    val addedUsers: List<User>? = null,
)

/** [BotCommand](https://core.telegram.org/bots/api#botcommand). */
data class BotCommand(
    val command: String = "",
    val description: String = "",
    val isEphemeral: Boolean? = null,
)

/** [BotCommandScopeAllChatAdministrators](https://core.telegram.org/bots/api#botcommandscopeallchatadministrators). */
data class BotCommandScopeAllChatAdministrators(
    val type: String = "",
) : BotCommandScope

/** [BotCommandScopeAllGroupChats](https://core.telegram.org/bots/api#botcommandscopeallgroupchats). */
data class BotCommandScopeAllGroupChats(
    val type: String = "",
) : BotCommandScope

/** [BotCommandScopeAllPrivateChats](https://core.telegram.org/bots/api#botcommandscopeallprivatechats). */
data class BotCommandScopeAllPrivateChats(
    val type: String = "",
) : BotCommandScope

/** [BotCommandScopeChat](https://core.telegram.org/bots/api#botcommandscopechat). */
data class BotCommandScopeChat(
    val type: String = "",
    val chatId: LongOrString? = null,
) : BotCommandScope

/** [BotCommandScopeChatAdministrators](https://core.telegram.org/bots/api#botcommandscopechatadministrators). */
data class BotCommandScopeChatAdministrators(
    val type: String = "",
    val chatId: LongOrString? = null,
) : BotCommandScope

/** [BotCommandScopeChatMember](https://core.telegram.org/bots/api#botcommandscopechatmember). */
data class BotCommandScopeChatMember(
    val type: String = "",
    val chatId: LongOrString? = null,
    val userId: Long = 0,
) : BotCommandScope

/** [BotCommandScopeDefault](https://core.telegram.org/bots/api#botcommandscopedefault). */
data class BotCommandScopeDefault(
    val type: String = "",
) : BotCommandScope

/** [BotDescription](https://core.telegram.org/bots/api#botdescription). */
data class BotDescription(
    val description: String = "",
)

/** [BotName](https://core.telegram.org/bots/api#botname). */
data class BotName(
    val name: String = "",
)

/** [BotShortDescription](https://core.telegram.org/bots/api#botshortdescription). */
data class BotShortDescription(
    val shortDescription: String = "",
)

/** [BotSubscriptionUpdated](https://core.telegram.org/bots/api#botsubscriptionupdated). */
data class BotSubscriptionUpdated(
    val user: User? = null,
    val invoicePayload: String = "",
    val state: String = "",
)

/** [BusinessBotRights](https://core.telegram.org/bots/api#businessbotrights). */
data class BusinessBotRights(
    val canReply: Boolean? = null,
    val canReadMessages: Boolean? = null,
    val canDeleteSentMessages: Boolean? = null,
    val canDeleteAllMessages: Boolean? = null,
    val canEditName: Boolean? = null,
    val canEditBio: Boolean? = null,
    val canEditProfilePhoto: Boolean? = null,
    val canEditUsername: Boolean? = null,
    val canChangeGiftSettings: Boolean? = null,
    val canViewGiftsAndStars: Boolean? = null,
    val canConvertGiftsToStars: Boolean? = null,
    val canTransferAndUpgradeGifts: Boolean? = null,
    val canTransferStars: Boolean? = null,
    val canManageStories: Boolean? = null,
)

/** [BusinessConnection](https://core.telegram.org/bots/api#businessconnection). */
data class BusinessConnection(
    val id: String = "",
    val user: User? = null,
    val userChatId: Long = 0,
    val date: Long = 0,
    val rights: BusinessBotRights? = null,
    val isEnabled: Boolean = false,
)

/** [BusinessIntro](https://core.telegram.org/bots/api#businessintro). */
data class BusinessIntro(
    val title: String? = null,
    val message: String? = null,
    val sticker: Sticker? = null,
)

/** [BusinessLocation](https://core.telegram.org/bots/api#businesslocation). */
data class BusinessLocation(
    val address: String = "",
    val location: Location? = null,
)

/** [BusinessMessagesDeleted](https://core.telegram.org/bots/api#businessmessagesdeleted). */
data class BusinessMessagesDeleted(
    val businessConnectionId: String = "",
    val chat: Chat? = null,
    val messageIds: List<Long> = emptyList(),
)

/** [BusinessOpeningHours](https://core.telegram.org/bots/api#businessopeninghours). */
data class BusinessOpeningHours(
    val timeZoneName: String = "",
    val openingHours: List<BusinessOpeningHoursInterval> = emptyList(),
)

/** [BusinessOpeningHoursInterval](https://core.telegram.org/bots/api#businessopeninghoursinterval). */
data class BusinessOpeningHoursInterval(
    val openingMinute: Long = 0,
    val closingMinute: Long = 0,
)

/** [CallbackGame](https://core.telegram.org/bots/api#callbackgame). */
data object CallbackGame

/** [CallbackQuery](https://core.telegram.org/bots/api#callbackquery). */
data class CallbackQuery(
    val id: String = "",
    val from: User? = null,
    val message: MaybeInaccessibleMessage? = null,
    val inlineMessageId: String? = null,
    val chatInstance: String = "",
    val data: String? = null,
    val gameShortName: String? = null,
)

/** [Chat](https://core.telegram.org/bots/api#chat). */
data class Chat(
    val id: Long = 0,
    val type: String = "",
    val title: String? = null,
    val username: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val isForum: Boolean? = null,
    val isDirectMessages: Boolean? = null,
)

/** [ChatAdministratorRights](https://core.telegram.org/bots/api#chatadministratorrights). */
data class ChatAdministratorRights(
    val isAnonymous: Boolean = false,
    val canManageChat: Boolean = false,
    val canDeleteMessages: Boolean = false,
    val canManageVideoChats: Boolean = false,
    val canRestrictMembers: Boolean = false,
    val canPromoteMembers: Boolean = false,
    val canChangeInfo: Boolean = false,
    val canInviteUsers: Boolean = false,
    val canPostStories: Boolean = false,
    val canEditStories: Boolean = false,
    val canDeleteStories: Boolean = false,
    val canPostMessages: Boolean? = null,
    val canEditMessages: Boolean? = null,
    val canPinMessages: Boolean? = null,
    val canManageTopics: Boolean? = null,
    val canManageDirectMessages: Boolean? = null,
    val canManageTags: Boolean? = null,
    val canSendWelcomeMessages: Boolean = false,
)

/** [ChatBackground](https://core.telegram.org/bots/api#chatbackground). */
data class ChatBackground(
    val type: BackgroundType? = null,
)

/** [ChatBoost](https://core.telegram.org/bots/api#chatboost). */
data class ChatBoost(
    val boostId: String = "",
    val addDate: Long = 0,
    val expirationDate: Long = 0,
    val source: ChatBoostSource? = null,
)

/** [ChatBoostAdded](https://core.telegram.org/bots/api#chatboostadded). */
data class ChatBoostAdded(
    val boostCount: Long = 0,
)

/** [ChatBoostRemoved](https://core.telegram.org/bots/api#chatboostremoved). */
data class ChatBoostRemoved(
    val chat: Chat? = null,
    val boostId: String = "",
    val removeDate: Long = 0,
    val source: ChatBoostSource? = null,
)

/** [ChatBoostSourceGiftCode](https://core.telegram.org/bots/api#chatboostsourcegiftcode). */
data class ChatBoostSourceGiftCode(
    val source: String = "",
    val user: User? = null,
) : ChatBoostSource

/** [ChatBoostSourceGiveaway](https://core.telegram.org/bots/api#chatboostsourcegiveaway). */
data class ChatBoostSourceGiveaway(
    val source: String = "",
    val giveawayMessageId: Long = 0,
    val user: User? = null,
    val prizeStarCount: Long? = null,
    val isUnclaimed: Boolean? = null,
) : ChatBoostSource

/** [ChatBoostSourcePremium](https://core.telegram.org/bots/api#chatboostsourcepremium). */
data class ChatBoostSourcePremium(
    val source: String = "",
    val user: User? = null,
) : ChatBoostSource

/** [ChatBoostUpdated](https://core.telegram.org/bots/api#chatboostupdated). */
data class ChatBoostUpdated(
    val chat: Chat? = null,
    val boost: ChatBoost? = null,
)

/** [ChatFullInfo](https://core.telegram.org/bots/api#chatfullinfo). */
data class ChatFullInfo(
    val id: Long = 0,
    val type: String = "",
    val title: String? = null,
    val username: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val isForum: Boolean? = null,
    val isDirectMessages: Boolean? = null,
    val accentColorId: Long = 0,
    val maxReactionCount: Long = 0,
    val photo: ChatPhoto? = null,
    val activeUsernames: List<String>? = null,
    val birthdate: Birthdate? = null,
    val businessIntro: BusinessIntro? = null,
    val businessLocation: BusinessLocation? = null,
    val businessOpeningHours: BusinessOpeningHours? = null,
    val personalChat: Chat? = null,
    val parentChat: Chat? = null,
    val availableReactions: List<ReactionType>? = null,
    val backgroundCustomEmojiId: String? = null,
    val profileAccentColorId: Long? = null,
    val profileBackgroundCustomEmojiId: String? = null,
    val emojiStatusCustomEmojiId: String? = null,
    val emojiStatusExpirationDate: Long? = null,
    val bio: String? = null,
    val hasPrivateForwards: Boolean? = null,
    val hasRestrictedVoiceAndVideoMessages: Boolean? = null,
    val joinToSendMessages: Boolean? = null,
    val joinByRequest: Boolean? = null,
    val description: String? = null,
    val inviteLink: String? = null,
    val pinnedMessage: Message? = null,
    val permissions: ChatPermissions? = null,
    val acceptedGiftTypes: AcceptedGiftTypes? = null,
    val canSendPaidMedia: Boolean? = null,
    val slowModeDelay: Long? = null,
    val unrestrictBoostCount: Long? = null,
    val messageAutoDeleteTime: Long? = null,
    val hasAggressiveAntiSpamEnabled: Boolean? = null,
    val hasHiddenMembers: Boolean? = null,
    val hasProtectedContent: Boolean? = null,
    val hasVisibleHistory: Boolean? = null,
    val stickerSetName: String? = null,
    val canSetStickerSet: Boolean? = null,
    val customEmojiStickerSetName: String? = null,
    val linkedChatId: Long? = null,
    val location: ChatLocation? = null,
    val rating: UserRating? = null,
    val firstProfileAudio: Audio? = null,
    val uniqueGiftColors: UniqueGiftColors? = null,
    val paidMessageStarCount: Long? = null,
    val guardBot: User? = null,
    val community: Community? = null,
)

/** [ChatInviteLink](https://core.telegram.org/bots/api#chatinvitelink). */
data class ChatInviteLink(
    val inviteLink: String = "",
    val creator: User? = null,
    val createsJoinRequest: Boolean = false,
    val isPrimary: Boolean = false,
    val isRevoked: Boolean = false,
    val name: String? = null,
    val expireDate: Long? = null,
    val memberLimit: Long? = null,
    val pendingJoinRequestCount: Long? = null,
    val subscriptionPeriod: Long? = null,
    val subscriptionPrice: Long? = null,
)

/** [ChatJoinRequest](https://core.telegram.org/bots/api#chatjoinrequest). */
data class ChatJoinRequest(
    val chat: Chat? = null,
    val from: User? = null,
    val userChatId: Long = 0,
    val date: Long = 0,
    val bio: String? = null,
    val inviteLink: ChatInviteLink? = null,
    val queryId: String? = null,
)

/** [ChatLocation](https://core.telegram.org/bots/api#chatlocation). */
data class ChatLocation(
    val location: Location? = null,
    val address: String = "",
)

/** [ChatMemberAdministrator](https://core.telegram.org/bots/api#chatmemberadministrator). */
data class ChatMemberAdministrator(
    val status: String = "",
    val user: User? = null,
    val canBeEdited: Boolean = false,
    val isAnonymous: Boolean = false,
    val canManageChat: Boolean = false,
    val canDeleteMessages: Boolean = false,
    val canManageVideoChats: Boolean = false,
    val canRestrictMembers: Boolean = false,
    val canPromoteMembers: Boolean = false,
    val canChangeInfo: Boolean = false,
    val canInviteUsers: Boolean = false,
    val canPostStories: Boolean = false,
    val canEditStories: Boolean = false,
    val canDeleteStories: Boolean = false,
    val canPostMessages: Boolean? = null,
    val canEditMessages: Boolean? = null,
    val canPinMessages: Boolean? = null,
    val canManageTopics: Boolean? = null,
    val canManageDirectMessages: Boolean? = null,
    val canManageTags: Boolean? = null,
    val canSendWelcomeMessages: Boolean = false,
    val customTitle: String? = null,
) : ChatMember

/** [ChatMemberBanned](https://core.telegram.org/bots/api#chatmemberbanned). */
data class ChatMemberBanned(
    val status: String = "",
    val user: User? = null,
    val untilDate: Long = 0,
) : ChatMember

/** [ChatMemberLeft](https://core.telegram.org/bots/api#chatmemberleft). */
data class ChatMemberLeft(
    val status: String = "",
    val user: User? = null,
) : ChatMember

/** [ChatMemberMember](https://core.telegram.org/bots/api#chatmembermember). */
data class ChatMemberMember(
    val status: String = "",
    val tag: String? = null,
    val user: User? = null,
    val untilDate: Long? = null,
) : ChatMember

/** [ChatMemberOwner](https://core.telegram.org/bots/api#chatmemberowner). */
data class ChatMemberOwner(
    val status: String = "",
    val user: User? = null,
    val isAnonymous: Boolean = false,
    val customTitle: String? = null,
) : ChatMember

/** [ChatMemberRestricted](https://core.telegram.org/bots/api#chatmemberrestricted). */
data class ChatMemberRestricted(
    val status: String = "",
    val tag: String? = null,
    val user: User? = null,
    val isMember: Boolean = false,
    val canSendMessages: Boolean = false,
    val canSendAudios: Boolean = false,
    val canSendDocuments: Boolean = false,
    val canSendPhotos: Boolean = false,
    val canSendVideos: Boolean = false,
    val canSendVideoNotes: Boolean = false,
    val canSendVoiceNotes: Boolean = false,
    val canSendPolls: Boolean = false,
    val canSendOtherMessages: Boolean = false,
    val canAddWebPagePreviews: Boolean = false,
    val canReactToMessages: Boolean = false,
    val canEditTag: Boolean = false,
    val canChangeInfo: Boolean = false,
    val canInviteUsers: Boolean = false,
    val canPinMessages: Boolean = false,
    val canManageTopics: Boolean = false,
    val untilDate: Long = 0,
) : ChatMember

/** [ChatMemberUpdated](https://core.telegram.org/bots/api#chatmemberupdated). */
data class ChatMemberUpdated(
    val chat: Chat? = null,
    val from: User? = null,
    val date: Long = 0,
    val oldChatMember: ChatMember? = null,
    val newChatMember: ChatMember? = null,
    val inviteLink: ChatInviteLink? = null,
    val viaJoinRequest: Boolean? = null,
    val viaChatFolderInviteLink: Boolean? = null,
)

/** [ChatOwnerChanged](https://core.telegram.org/bots/api#chatownerchanged). */
data class ChatOwnerChanged(
    val newOwner: User? = null,
)

/** [ChatOwnerLeft](https://core.telegram.org/bots/api#chatownerleft). */
data class ChatOwnerLeft(
    val newOwner: User? = null,
)

/** [ChatPermissions](https://core.telegram.org/bots/api#chatpermissions). */
data class ChatPermissions(
    val canSendMessages: Boolean? = null,
    val canSendAudios: Boolean? = null,
    val canSendDocuments: Boolean? = null,
    val canSendPhotos: Boolean? = null,
    val canSendVideos: Boolean? = null,
    val canSendVideoNotes: Boolean? = null,
    val canSendVoiceNotes: Boolean? = null,
    val canSendPolls: Boolean? = null,
    val canSendOtherMessages: Boolean? = null,
    val canAddWebPagePreviews: Boolean? = null,
    val canReactToMessages: Boolean? = null,
    val canEditTag: Boolean? = null,
    val canChangeInfo: Boolean? = null,
    val canInviteUsers: Boolean? = null,
    val canPinMessages: Boolean? = null,
    val canManageTopics: Boolean? = null,
)

/** [ChatPhoto](https://core.telegram.org/bots/api#chatphoto). */
data class ChatPhoto(
    val smallFileId: String = "",
    val smallFileUniqueId: String = "",
    val bigFileId: String = "",
    val bigFileUniqueId: String = "",
)

/** [ChatShared](https://core.telegram.org/bots/api#chatshared). */
data class ChatShared(
    val requestId: Long = 0,
    val chatId: Long = 0,
    val title: String? = null,
    val username: String? = null,
    val photo: List<PhotoSize>? = null,
)

/** [Checklist](https://core.telegram.org/bots/api#checklist). */
data class Checklist(
    val title: String = "",
    val titleEntities: List<MessageEntity>? = null,
    val tasks: List<ChecklistTask> = emptyList(),
    val othersCanAddTasks: Boolean? = null,
    val othersCanMarkTasksAsDone: Boolean? = null,
)

/** [ChecklistTask](https://core.telegram.org/bots/api#checklisttask). */
data class ChecklistTask(
    val id: Long = 0,
    val text: String = "",
    val textEntities: List<MessageEntity>? = null,
    val completedByUser: User? = null,
    val completedByChat: Chat? = null,
    val completionDate: Long? = null,
)

/** [ChecklistTasksAdded](https://core.telegram.org/bots/api#checklisttasksadded). */
data class ChecklistTasksAdded(
    val checklistMessage: Message? = null,
    val tasks: List<ChecklistTask> = emptyList(),
)

/** [ChecklistTasksDone](https://core.telegram.org/bots/api#checklisttasksdone). */
data class ChecklistTasksDone(
    val checklistMessage: Message? = null,
    val markedAsDoneTaskIds: List<Long>? = null,
    val markedAsNotDoneTaskIds: List<Long>? = null,
)

/** [ChosenInlineResult](https://core.telegram.org/bots/api#choseninlineresult). */
data class ChosenInlineResult(
    val resultId: String = "",
    val from: User? = null,
    val location: Location? = null,
    val inlineMessageId: String? = null,
    val query: String = "",
)

/** [Community](https://core.telegram.org/bots/api#community). */
data class Community(
    val id: Long = 0,
    val name: String = "",
)

/** [CommunityChatAdded](https://core.telegram.org/bots/api#communitychatadded). */
data class CommunityChatAdded(
    val community: Community? = null,
)

/** [CommunityChatJoined](https://core.telegram.org/bots/api#communitychatjoined). */
data class CommunityChatJoined(
    val community: Community? = null,
)

/** [CommunityChatRemoved](https://core.telegram.org/bots/api#communitychatremoved). */
data object CommunityChatRemoved

/** [Contact](https://core.telegram.org/bots/api#contact). */
data class Contact(
    val phoneNumber: String = "",
    val firstName: String = "",
    val lastName: String? = null,
    val userId: Long? = null,
    val vcard: String? = null,
)

/** [CopyTextButton](https://core.telegram.org/bots/api#copytextbutton). */
data class CopyTextButton(
    val text: String = "",
)

/** [Dice](https://core.telegram.org/bots/api#dice). */
data class Dice(
    val emoji: String = "",
    val value: Long = 0,
)

/** [DirectMessagePriceChanged](https://core.telegram.org/bots/api#directmessagepricechanged). */
data class DirectMessagePriceChanged(
    val areDirectMessagesEnabled: Boolean = false,
    val directMessageStarCount: Long? = null,
)

/** [DirectMessagesTopic](https://core.telegram.org/bots/api#directmessagestopic). */
data class DirectMessagesTopic(
    val topicId: Long = 0,
    val user: User? = null,
)

/** [DisabledButton](https://core.telegram.org/bots/api#disabledbutton). */
data object DisabledButton

/** [Document](https://core.telegram.org/bots/api#document). */
data class Document(
    val fileId: String = "",
    val fileUniqueId: String = "",
    val thumbnail: PhotoSize? = null,
    val fileName: String? = null,
    val mimeType: String? = null,
    val fileSize: Long? = null,
)

/** [EncryptedCredentials](https://core.telegram.org/bots/api#encryptedcredentials). */
data class EncryptedCredentials(
    val data: String = "",
    val hash: String = "",
    val secret: String = "",
)

/** [EncryptedPassportElement](https://core.telegram.org/bots/api#encryptedpassportelement). */
data class EncryptedPassportElement(
    val type: String = "",
    val data: String? = null,
    val phoneNumber: String? = null,
    val email: String? = null,
    val files: List<PassportFile>? = null,
    val frontSide: PassportFile? = null,
    val reverseSide: PassportFile? = null,
    val selfie: PassportFile? = null,
    val translation: List<PassportFile>? = null,
    val hash: String = "",
)

/** [EphemeralMessageParameters](https://core.telegram.org/bots/api#ephemeralmessageparameters). */
data class EphemeralMessageParameters(
    val receiverUserId: Long = 0,
    val callbackQueryId: String? = null,
    val replaceCallbackQueryMessage: Boolean? = null,
)

/** [ExternalReplyInfo](https://core.telegram.org/bots/api#externalreplyinfo). */
data class ExternalReplyInfo(
    val origin: MessageOrigin? = null,
    val chat: Chat? = null,
    val messageId: Long? = null,
    val linkPreviewOptions: LinkPreviewOptions? = null,
    val animation: Animation? = null,
    val audio: Audio? = null,
    val document: Document? = null,
    val livePhoto: LivePhoto? = null,
    val paidMedia: PaidMediaInfo? = null,
    val photo: List<PhotoSize>? = null,
    val sticker: Sticker? = null,
    val story: Story? = null,
    val video: Video? = null,
    val videoNote: VideoNote? = null,
    val voice: Voice? = null,
    val hasMediaSpoiler: Boolean? = null,
    val checklist: Checklist? = null,
    val contact: Contact? = null,
    val dice: Dice? = null,
    val game: Game? = null,
    val giveaway: Giveaway? = null,
    val giveawayWinners: GiveawayWinners? = null,
    val invoice: Invoice? = null,
    val location: Location? = null,
    val poll: Poll? = null,
    val venue: Venue? = null,
)

/** [File](https://core.telegram.org/bots/api#file). */
data class File(
    val fileId: String = "",
    val fileUniqueId: String = "",
    val fileSize: Long? = null,
    val filePath: String? = null,
)

/** [ForceReply](https://core.telegram.org/bots/api#forcereply). */
data class ForceReply(
    val forceReply: Boolean = false,
    val inputFieldPlaceholder: String? = null,
    val selective: Boolean? = null,
)

/** [ForumTopic](https://core.telegram.org/bots/api#forumtopic). */
data class ForumTopic(
    val messageThreadId: Long = 0,
    val name: String = "",
    val iconColor: Long = 0,
    val iconCustomEmojiId: String? = null,
    val isNameImplicit: Boolean? = null,
)

/** [ForumTopicClosed](https://core.telegram.org/bots/api#forumtopicclosed). */
data object ForumTopicClosed

/** [ForumTopicCreated](https://core.telegram.org/bots/api#forumtopiccreated). */
data class ForumTopicCreated(
    val name: String = "",
    val iconColor: Long = 0,
    val iconCustomEmojiId: String? = null,
    val isNameImplicit: Boolean? = null,
)

/** [ForumTopicEdited](https://core.telegram.org/bots/api#forumtopicedited). */
data class ForumTopicEdited(
    val name: String? = null,
    val iconCustomEmojiId: String? = null,
)

/** [ForumTopicReopened](https://core.telegram.org/bots/api#forumtopicreopened). */
data object ForumTopicReopened

/** [Game](https://core.telegram.org/bots/api#game). */
data class Game(
    val title: String = "",
    val description: String = "",
    val photo: List<PhotoSize> = emptyList(),
    val text: String? = null,
    val textEntities: List<MessageEntity>? = null,
    val animation: Animation? = null,
)

/** [GameHighScore](https://core.telegram.org/bots/api#gamehighscore). */
data class GameHighScore(
    val position: Long = 0,
    val user: User? = null,
    val score: Long = 0,
)

/** [GeneralForumTopicHidden](https://core.telegram.org/bots/api#generalforumtopichidden). */
data object GeneralForumTopicHidden

/** [GeneralForumTopicUnhidden](https://core.telegram.org/bots/api#generalforumtopicunhidden). */
data object GeneralForumTopicUnhidden

/** [Gift](https://core.telegram.org/bots/api#gift). */
data class Gift(
    val id: String = "",
    val sticker: Sticker? = null,
    val starCount: Long = 0,
    val upgradeStarCount: Long? = null,
    val isPremium: Boolean? = null,
    val hasColors: Boolean? = null,
    val totalCount: Long? = null,
    val remainingCount: Long? = null,
    val personalTotalCount: Long? = null,
    val personalRemainingCount: Long? = null,
    val background: GiftBackground? = null,
    val uniqueGiftVariantCount: Long? = null,
    val publisherChat: Chat? = null,
)

/** [GiftBackground](https://core.telegram.org/bots/api#giftbackground). */
data class GiftBackground(
    val centerColor: Long = 0,
    val edgeColor: Long = 0,
    val textColor: Long = 0,
)

/** [GiftInfo](https://core.telegram.org/bots/api#giftinfo). */
data class GiftInfo(
    val gift: Gift? = null,
    val ownedGiftId: String? = null,
    val convertStarCount: Long? = null,
    val prepaidUpgradeStarCount: Long? = null,
    val isUpgradeSeparate: Boolean? = null,
    val canBeUpgraded: Boolean? = null,
    val text: String? = null,
    val entities: List<MessageEntity>? = null,
    val isPrivate: Boolean? = null,
    val uniqueGiftNumber: Long? = null,
)

/** [Gifts](https://core.telegram.org/bots/api#gifts). */
data class Gifts(
    val gifts: List<Gift> = emptyList(),
)

/** [Giveaway](https://core.telegram.org/bots/api#giveaway). */
data class Giveaway(
    val chats: List<Chat> = emptyList(),
    val winnersSelectionDate: Long = 0,
    val winnerCount: Long = 0,
    val onlyNewMembers: Boolean? = null,
    val hasPublicWinners: Boolean? = null,
    val prizeDescription: String? = null,
    val countryCodes: List<String>? = null,
    val prizeStarCount: Long? = null,
    val premiumSubscriptionMonthCount: Long? = null,
)

/** [GiveawayCompleted](https://core.telegram.org/bots/api#giveawaycompleted). */
data class GiveawayCompleted(
    val winnerCount: Long = 0,
    val unclaimedPrizeCount: Long? = null,
    val giveawayMessage: Message? = null,
    val isStarGiveaway: Boolean? = null,
)

/** [GiveawayCreated](https://core.telegram.org/bots/api#giveawaycreated). */
data class GiveawayCreated(
    val prizeStarCount: Long? = null,
)

/** [GiveawayWinners](https://core.telegram.org/bots/api#giveawaywinners). */
data class GiveawayWinners(
    val chat: Chat? = null,
    val giveawayMessageId: Long = 0,
    val winnersSelectionDate: Long = 0,
    val winnerCount: Long = 0,
    val winners: List<User> = emptyList(),
    val additionalChatCount: Long? = null,
    val prizeStarCount: Long? = null,
    val premiumSubscriptionMonthCount: Long? = null,
    val unclaimedPrizeCount: Long? = null,
    val onlyNewMembers: Boolean? = null,
    val wasRefunded: Boolean? = null,
    val prizeDescription: String? = null,
)

/** [InaccessibleMessage](https://core.telegram.org/bots/api#inaccessiblemessage). */
data class InaccessibleMessage(
    val chat: Chat? = null,
    val messageId: Long = 0,
    val date: Long = 0,
) : MaybeInaccessibleMessage

/** [InlineKeyboardButton](https://core.telegram.org/bots/api#inlinekeyboardbutton). */
data class InlineKeyboardButton(
    val text: String = "",
    val iconCustomEmojiId: String? = null,
    val style: String? = null,
    val url: String? = null,
    val callbackData: String? = null,
    val webApp: WebAppInfo? = null,
    val loginUrl: LoginUrl? = null,
    val switchInlineQuery: String? = null,
    val switchInlineQueryCurrentChat: String? = null,
    val switchInlineQueryChosenChat: SwitchInlineQueryChosenChat? = null,
    val copyText: CopyTextButton? = null,
    val callbackGame: CallbackGame? = null,
    val pay: Boolean? = null,
    val disabled: DisabledButton? = null,
)

/** [InlineKeyboardMarkup](https://core.telegram.org/bots/api#inlinekeyboardmarkup). */
data class InlineKeyboardMarkup(
    val inlineKeyboard: List<List<InlineKeyboardButton>> = emptyList(),
    val forceReply: Boolean? = null,
)

/** [InlineQuery](https://core.telegram.org/bots/api#inlinequery). */
data class InlineQuery(
    val id: String = "",
    val from: User? = null,
    val query: String = "",
    val offset: String = "",
    val chatType: String? = null,
    val location: Location? = null,
)

/** [InlineQueryResultArticle](https://core.telegram.org/bots/api#inlinequeryresultarticle). */
data class InlineQueryResultArticle(
    val type: String = "",
    val id: String = "",
    val title: String = "",
    val inputMessageContent: InputMessageContent? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val url: String? = null,
    val description: String? = null,
    val thumbnailUrl: String? = null,
    val thumbnailWidth: Long? = null,
    val thumbnailHeight: Long? = null,
) : InlineQueryResult

/** [InlineQueryResultAudio](https://core.telegram.org/bots/api#inlinequeryresultaudio). */
data class InlineQueryResultAudio(
    val type: String = "",
    val id: String = "",
    val audioUrl: String = "",
    val title: String = "",
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val performer: String? = null,
    val audioDuration: Long? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
) : InlineQueryResult

/** [InlineQueryResultCachedAudio](https://core.telegram.org/bots/api#inlinequeryresultcachedaudio). */
data class InlineQueryResultCachedAudio(
    val type: String = "",
    val id: String = "",
    val audioFileId: String = "",
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
) : InlineQueryResult

/** [InlineQueryResultCachedDocument](https://core.telegram.org/bots/api#inlinequeryresultcacheddocument). */
data class InlineQueryResultCachedDocument(
    val type: String = "",
    val id: String = "",
    val title: String = "",
    val documentFileId: String = "",
    val description: String? = null,
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
) : InlineQueryResult

/** [InlineQueryResultCachedGif](https://core.telegram.org/bots/api#inlinequeryresultcachedgif). */
data class InlineQueryResultCachedGif(
    val type: String = "",
    val id: String = "",
    val gifFileId: String = "",
    val title: String? = null,
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val showCaptionAboveMedia: Boolean? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
) : InlineQueryResult

/** [InlineQueryResultCachedMpeg4Gif](https://core.telegram.org/bots/api#inlinequeryresultcachedmpeg4gif). */
data class InlineQueryResultCachedMpeg4Gif(
    val type: String = "",
    val id: String = "",
    val mpeg4FileId: String = "",
    val title: String? = null,
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val showCaptionAboveMedia: Boolean? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
) : InlineQueryResult

/** [InlineQueryResultCachedPhoto](https://core.telegram.org/bots/api#inlinequeryresultcachedphoto). */
data class InlineQueryResultCachedPhoto(
    val type: String = "",
    val id: String = "",
    val photoFileId: String = "",
    val title: String? = null,
    val description: String? = null,
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val showCaptionAboveMedia: Boolean? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
) : InlineQueryResult

/** [InlineQueryResultCachedSticker](https://core.telegram.org/bots/api#inlinequeryresultcachedsticker). */
data class InlineQueryResultCachedSticker(
    val type: String = "",
    val id: String = "",
    val stickerFileId: String = "",
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
) : InlineQueryResult

/** [InlineQueryResultCachedVideo](https://core.telegram.org/bots/api#inlinequeryresultcachedvideo). */
data class InlineQueryResultCachedVideo(
    val type: String = "",
    val id: String = "",
    val videoFileId: String = "",
    val title: String = "",
    val description: String? = null,
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val showCaptionAboveMedia: Boolean? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
) : InlineQueryResult

/** [InlineQueryResultCachedVoice](https://core.telegram.org/bots/api#inlinequeryresultcachedvoice). */
data class InlineQueryResultCachedVoice(
    val type: String = "",
    val id: String = "",
    val voiceFileId: String = "",
    val title: String = "",
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
) : InlineQueryResult

/** [InlineQueryResultContact](https://core.telegram.org/bots/api#inlinequeryresultcontact). */
data class InlineQueryResultContact(
    val type: String = "",
    val id: String = "",
    val phoneNumber: String = "",
    val firstName: String = "",
    val lastName: String? = null,
    val vcard: String? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
    val thumbnailUrl: String? = null,
    val thumbnailWidth: Long? = null,
    val thumbnailHeight: Long? = null,
) : InlineQueryResult

/** [InlineQueryResultDocument](https://core.telegram.org/bots/api#inlinequeryresultdocument). */
data class InlineQueryResultDocument(
    val type: String = "",
    val id: String = "",
    val title: String = "",
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val documentUrl: String = "",
    val mimeType: String = "",
    val description: String? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
    val thumbnailUrl: String? = null,
    val thumbnailWidth: Long? = null,
    val thumbnailHeight: Long? = null,
) : InlineQueryResult

/** [InlineQueryResultGame](https://core.telegram.org/bots/api#inlinequeryresultgame). */
data class InlineQueryResultGame(
    val type: String = "",
    val id: String = "",
    val gameShortName: String = "",
    val replyMarkup: InlineKeyboardMarkup? = null,
) : InlineQueryResult

/** [InlineQueryResultGif](https://core.telegram.org/bots/api#inlinequeryresultgif). */
data class InlineQueryResultGif(
    val type: String = "",
    val id: String = "",
    val gifUrl: String = "",
    val gifWidth: Long? = null,
    val gifHeight: Long? = null,
    val gifDuration: Long? = null,
    val thumbnailUrl: String = "",
    val thumbnailMimeType: String? = null,
    val title: String? = null,
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val showCaptionAboveMedia: Boolean? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
) : InlineQueryResult

/** [InlineQueryResultLocation](https://core.telegram.org/bots/api#inlinequeryresultlocation). */
data class InlineQueryResultLocation(
    val type: String = "",
    val id: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val title: String = "",
    val horizontalAccuracy: Double? = null,
    val livePeriod: Long? = null,
    val heading: Long? = null,
    val proximityAlertRadius: Long? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
    val thumbnailUrl: String? = null,
    val thumbnailWidth: Long? = null,
    val thumbnailHeight: Long? = null,
) : InlineQueryResult

/** [InlineQueryResultMpeg4Gif](https://core.telegram.org/bots/api#inlinequeryresultmpeg4gif). */
data class InlineQueryResultMpeg4Gif(
    val type: String = "",
    val id: String = "",
    val mpeg4Url: String = "",
    val mpeg4Width: Long? = null,
    val mpeg4Height: Long? = null,
    val mpeg4Duration: Long? = null,
    val thumbnailUrl: String = "",
    val thumbnailMimeType: String? = null,
    val title: String? = null,
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val showCaptionAboveMedia: Boolean? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
) : InlineQueryResult

/** [InlineQueryResultPhoto](https://core.telegram.org/bots/api#inlinequeryresultphoto). */
data class InlineQueryResultPhoto(
    val type: String = "",
    val id: String = "",
    val photoUrl: String = "",
    val thumbnailUrl: String = "",
    val photoWidth: Long? = null,
    val photoHeight: Long? = null,
    val title: String? = null,
    val description: String? = null,
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val showCaptionAboveMedia: Boolean? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
) : InlineQueryResult

/** [InlineQueryResultVenue](https://core.telegram.org/bots/api#inlinequeryresultvenue). */
data class InlineQueryResultVenue(
    val type: String = "",
    val id: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val title: String = "",
    val address: String = "",
    val foursquareId: String? = null,
    val foursquareType: String? = null,
    val googlePlaceId: String? = null,
    val googlePlaceType: String? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
    val thumbnailUrl: String? = null,
    val thumbnailWidth: Long? = null,
    val thumbnailHeight: Long? = null,
) : InlineQueryResult

/** [InlineQueryResultVideo](https://core.telegram.org/bots/api#inlinequeryresultvideo). */
data class InlineQueryResultVideo(
    val type: String = "",
    val id: String = "",
    val videoUrl: String = "",
    val mimeType: String = "",
    val thumbnailUrl: String = "",
    val title: String = "",
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val showCaptionAboveMedia: Boolean? = null,
    val videoWidth: Long? = null,
    val videoHeight: Long? = null,
    val videoDuration: Long? = null,
    val description: String? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
) : InlineQueryResult

/** [InlineQueryResultVoice](https://core.telegram.org/bots/api#inlinequeryresultvoice). */
data class InlineQueryResultVoice(
    val type: String = "",
    val id: String = "",
    val voiceUrl: String = "",
    val title: String = "",
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val voiceDuration: Long? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
) : InlineQueryResult

/** [InlineQueryResultsButton](https://core.telegram.org/bots/api#inlinequeryresultsbutton). */
data class InlineQueryResultsButton(
    val text: String = "",
    val webApp: WebAppInfo? = null,
    val startParameter: String? = null,
)

/** [InputChecklist](https://core.telegram.org/bots/api#inputchecklist). */
data class InputChecklist(
    val title: String = "",
    val parseMode: String? = null,
    val titleEntities: List<MessageEntity>? = null,
    val tasks: List<InputChecklistTask> = emptyList(),
    val othersCanAddTasks: Boolean? = null,
    val othersCanMarkTasksAsDone: Boolean? = null,
)

/** [InputChecklistTask](https://core.telegram.org/bots/api#inputchecklisttask). */
data class InputChecklistTask(
    val id: Long = 0,
    val text: String = "",
    val parseMode: String? = null,
    val textEntities: List<MessageEntity>? = null,
)

/** [InputContactMessageContent](https://core.telegram.org/bots/api#inputcontactmessagecontent). */
data class InputContactMessageContent(
    val phoneNumber: String = "",
    val firstName: String = "",
    val lastName: String? = null,
    val vcard: String? = null,
) : InputMessageContent

/** [InputFile](https://core.telegram.org/bots/api#inputfile). Path, file_id, or attach:// name. */
data class InputFile(
    val value: String = "",
)

/** [InputInvoiceMessageContent](https://core.telegram.org/bots/api#inputinvoicemessagecontent). */
data class InputInvoiceMessageContent(
    val title: String = "",
    val description: String = "",
    val payload: String = "",
    val providerToken: String? = null,
    val currency: String = "",
    val prices: List<LabeledPrice> = emptyList(),
    val maxTipAmount: Long? = null,
    val suggestedTipAmounts: List<Long>? = null,
    val providerData: String? = null,
    val photoUrl: String? = null,
    val photoSize: Long? = null,
    val photoWidth: Long? = null,
    val photoHeight: Long? = null,
    val needName: Boolean? = null,
    val needPhoneNumber: Boolean? = null,
    val needEmail: Boolean? = null,
    val needShippingAddress: Boolean? = null,
    val sendPhoneNumberToProvider: Boolean? = null,
    val sendEmailToProvider: Boolean? = null,
    val isFlexible: Boolean? = null,
) : InputMessageContent

/** [InputLocationMessageContent](https://core.telegram.org/bots/api#inputlocationmessagecontent). */
data class InputLocationMessageContent(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val horizontalAccuracy: Double? = null,
    val livePeriod: Long? = null,
    val heading: Long? = null,
    val proximityAlertRadius: Long? = null,
) : InputMessageContent

/** [InputMediaAnimation](https://core.telegram.org/bots/api#inputmediaanimation). */
data class InputMediaAnimation(
    val type: String = "",
    val media: String = "",
    val thumbnail: String? = null,
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val showCaptionAboveMedia: Boolean? = null,
    val width: Long? = null,
    val height: Long? = null,
    val duration: Long? = null,
    val hasSpoiler: Boolean? = null,
) : InputPollMedia, InputPollOptionMedia, InputMedia, MediaOfInputRichMessageMedia

/** [InputMediaAudio](https://core.telegram.org/bots/api#inputmediaaudio). */
data class InputMediaAudio(
    val type: String = "",
    val media: String = "",
    val thumbnail: String? = null,
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val duration: Long? = null,
    val performer: String? = null,
    val title: String? = null,
) : InputPollMedia, InputMedia, MediaOfInputRichMessageMedia

/** [InputMediaDocument](https://core.telegram.org/bots/api#inputmediadocument). */
data class InputMediaDocument(
    val type: String = "",
    val media: String = "",
    val thumbnail: String? = null,
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val disableContentTypeDetection: Boolean? = null,
) : InputPollMedia, InputMedia, MediaOfInputRichMessageMedia

/** [InputMediaLink](https://core.telegram.org/bots/api#inputmedialink). */
data class InputMediaLink(
    val type: String = "",
    val url: String = "",
) : InputPollOptionMedia

/** [InputMediaLivePhoto](https://core.telegram.org/bots/api#inputmedialivephoto). */
data class InputMediaLivePhoto(
    val type: String = "",
    val media: String = "",
    val photo: String = "",
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val showCaptionAboveMedia: Boolean? = null,
    val hasSpoiler: Boolean? = null,
) : InputPollMedia, InputPollOptionMedia, InputMedia

/** [InputMediaLocation](https://core.telegram.org/bots/api#inputmedialocation). */
data class InputMediaLocation(
    val type: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val horizontalAccuracy: Double? = null,
) : InputPollMedia, InputPollOptionMedia

/** [InputMediaPhoto](https://core.telegram.org/bots/api#inputmediaphoto). */
data class InputMediaPhoto(
    val type: String = "",
    val media: String = "",
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val showCaptionAboveMedia: Boolean? = null,
    val hasSpoiler: Boolean? = null,
) : InputPollMedia, InputPollOptionMedia, InputMedia, MediaOfInputRichMessageMedia

/** [InputMediaSticker](https://core.telegram.org/bots/api#inputmediasticker). */
data class InputMediaSticker(
    val type: String = "",
    val media: String = "",
    val emoji: String? = null,
) : InputPollOptionMedia

/** [InputMediaVenue](https://core.telegram.org/bots/api#inputmediavenue). */
data class InputMediaVenue(
    val type: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val title: String = "",
    val address: String = "",
    val foursquareId: String? = null,
    val foursquareType: String? = null,
    val googlePlaceId: String? = null,
    val googlePlaceType: String? = null,
) : InputPollMedia, InputPollOptionMedia

/** [InputMediaVideo](https://core.telegram.org/bots/api#inputmediavideo). */
data class InputMediaVideo(
    val type: String = "",
    val media: String = "",
    val thumbnail: String? = null,
    val cover: String? = null,
    val startTimestamp: Long? = null,
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val showCaptionAboveMedia: Boolean? = null,
    val width: Long? = null,
    val height: Long? = null,
    val duration: Long? = null,
    val supportsStreaming: Boolean? = null,
    val hasSpoiler: Boolean? = null,
) : InputPollMedia, InputPollOptionMedia, InputMedia, MediaOfInputRichMessageMedia

/** [InputMediaVoiceNote](https://core.telegram.org/bots/api#inputmediavoicenote). */
data class InputMediaVoiceNote(
    val type: String = "",
    val media: String = "",
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val duration: Long? = null,
) : MediaOfInputRichMessageMedia

/** [InputPaidMediaLivePhoto](https://core.telegram.org/bots/api#inputpaidmedialivephoto). */
data class InputPaidMediaLivePhoto(
    val type: String = "",
    val media: String = "",
    val photo: String = "",
) : InputPaidMedia

/** [InputPaidMediaPhoto](https://core.telegram.org/bots/api#inputpaidmediaphoto). */
data class InputPaidMediaPhoto(
    val type: String = "",
    val media: String = "",
) : InputPaidMedia

/** [InputPaidMediaVideo](https://core.telegram.org/bots/api#inputpaidmediavideo). */
data class InputPaidMediaVideo(
    val type: String = "",
    val media: String = "",
    val thumbnail: String? = null,
    val cover: String? = null,
    val startTimestamp: Long? = null,
    val width: Long? = null,
    val height: Long? = null,
    val duration: Long? = null,
    val supportsStreaming: Boolean? = null,
) : InputPaidMedia

/** [InputPollOption](https://core.telegram.org/bots/api#inputpolloption). */
data class InputPollOption(
    val text: String = "",
    val textParseMode: String? = null,
    val textEntities: List<MessageEntity>? = null,
    val media: InputPollOptionMedia? = null,
)

/** [InputProfilePhotoAnimated](https://core.telegram.org/bots/api#inputprofilephotoanimated). */
data class InputProfilePhotoAnimated(
    val type: String = "",
    val animation: String = "",
    val mainFrameTimestamp: Double? = null,
) : InputProfilePhoto

/** [InputProfilePhotoStatic](https://core.telegram.org/bots/api#inputprofilephotostatic). */
data class InputProfilePhotoStatic(
    val type: String = "",
    val photo: String = "",
) : InputProfilePhoto

/** [InputRichBlockAnchor](https://core.telegram.org/bots/api#inputrichblockanchor). */
data class InputRichBlockAnchor(
    val type: String = "",
    val name: String = "",
) : InputRichBlock

/** [InputRichBlockAnimation](https://core.telegram.org/bots/api#inputrichblockanimation). */
data class InputRichBlockAnimation(
    val type: String = "",
    val animation: InputMediaAnimation? = null,
    val caption: RichBlockCaption? = null,
) : InputRichBlock

/** [InputRichBlockAudio](https://core.telegram.org/bots/api#inputrichblockaudio). */
data class InputRichBlockAudio(
    val type: String = "",
    val audio: InputMediaAudio? = null,
    val caption: RichBlockCaption? = null,
) : InputRichBlock

/** [InputRichBlockBlockQuotation](https://core.telegram.org/bots/api#inputrichblockblockquotation). */
data class InputRichBlockBlockQuotation(
    val type: String = "",
    val blocks: List<InputRichBlock> = emptyList(),
    val credit: RichText? = null,
) : InputRichBlock

/** [InputRichBlockButtons](https://core.telegram.org/bots/api#inputrichblockbuttons). */
data class InputRichBlockButtons(
    val type: String = "",
    val buttons: List<RichMessageButton> = emptyList(),
    val align: String? = null,
) : InputRichBlock

/** [InputRichBlockCollage](https://core.telegram.org/bots/api#inputrichblockcollage). */
data class InputRichBlockCollage(
    val type: String = "",
    val blocks: List<InputRichBlock> = emptyList(),
    val caption: RichBlockCaption? = null,
) : InputRichBlock

/** [InputRichBlockDetails](https://core.telegram.org/bots/api#inputrichblockdetails). */
data class InputRichBlockDetails(
    val type: String = "",
    val summary: RichText? = null,
    val blocks: List<InputRichBlock> = emptyList(),
    val isOpen: Boolean? = null,
) : InputRichBlock

/** [InputRichBlockDivider](https://core.telegram.org/bots/api#inputrichblockdivider). */
data class InputRichBlockDivider(
    val type: String = "",
) : InputRichBlock

/** [InputRichBlockDocument](https://core.telegram.org/bots/api#inputrichblockdocument). */
data class InputRichBlockDocument(
    val type: String = "",
    val document: InputMediaDocument? = null,
    val caption: RichBlockCaption? = null,
) : InputRichBlock

/** [InputRichBlockExpandableBlockQuotation](https://core.telegram.org/bots/api#inputrichblockexpandableblockquotation). */
data class InputRichBlockExpandableBlockQuotation(
    val type: String = "",
    val text: RichText? = null,
    val credit: RichText? = null,
) : InputRichBlock

/** [InputRichBlockFooter](https://core.telegram.org/bots/api#inputrichblockfooter). */
data class InputRichBlockFooter(
    val type: String = "",
    val text: RichText? = null,
) : InputRichBlock

/** [InputRichBlockList](https://core.telegram.org/bots/api#inputrichblocklist). */
data class InputRichBlockList(
    val type: String = "",
    val items: List<InputRichBlockListItem> = emptyList(),
) : InputRichBlock

/** [InputRichBlockListItem](https://core.telegram.org/bots/api#inputrichblocklistitem). */
data class InputRichBlockListItem(
    val blocks: List<InputRichBlock> = emptyList(),
    val hasCheckbox: Boolean? = null,
    val isChecked: Boolean? = null,
    val value: Long? = null,
    val type: String? = null,
)

/** [InputRichBlockMap](https://core.telegram.org/bots/api#inputrichblockmap). */
data class InputRichBlockMap(
    val type: String = "",
    val location: Location? = null,
    val zoom: Long? = null,
    val width: Long? = null,
    val height: Long? = null,
    val caption: RichBlockCaption? = null,
) : InputRichBlock

/** [InputRichBlockMathematicalExpression](https://core.telegram.org/bots/api#inputrichblockmathematicalexpression). */
data class InputRichBlockMathematicalExpression(
    val type: String = "",
    val expression: String = "",
) : InputRichBlock

/** [InputRichBlockParagraph](https://core.telegram.org/bots/api#inputrichblockparagraph). */
data class InputRichBlockParagraph(
    val type: String = "",
    val text: RichText? = null,
) : InputRichBlock

/** [InputRichBlockPhoto](https://core.telegram.org/bots/api#inputrichblockphoto). */
data class InputRichBlockPhoto(
    val type: String = "",
    val photo: InputMediaPhoto? = null,
    val caption: RichBlockCaption? = null,
) : InputRichBlock

/** [InputRichBlockPreformatted](https://core.telegram.org/bots/api#inputrichblockpreformatted). */
data class InputRichBlockPreformatted(
    val type: String = "",
    val text: RichText? = null,
    val language: String? = null,
) : InputRichBlock

/** [InputRichBlockPullQuotation](https://core.telegram.org/bots/api#inputrichblockpullquotation). */
data class InputRichBlockPullQuotation(
    val type: String = "",
    val text: RichText? = null,
    val credit: RichText? = null,
) : InputRichBlock

/** [InputRichBlockSectionHeading](https://core.telegram.org/bots/api#inputrichblocksectionheading). */
data class InputRichBlockSectionHeading(
    val type: String = "",
    val text: RichText? = null,
    val size: Long = 0,
) : InputRichBlock

/** [InputRichBlockSlideshow](https://core.telegram.org/bots/api#inputrichblockslideshow). */
data class InputRichBlockSlideshow(
    val type: String = "",
    val blocks: List<InputRichBlock> = emptyList(),
    val caption: RichBlockCaption? = null,
) : InputRichBlock

/** [InputRichBlockTable](https://core.telegram.org/bots/api#inputrichblocktable). */
data class InputRichBlockTable(
    val type: String = "",
    val cells: List<List<RichBlockTableCell>> = emptyList(),
    val isBordered: Boolean? = null,
    val isStriped: Boolean? = null,
    val isCompact: Boolean? = null,
    val caption: RichText? = null,
) : InputRichBlock

/** [InputRichBlockThinking](https://core.telegram.org/bots/api#inputrichblockthinking). */
data class InputRichBlockThinking(
    val type: String = "",
    val text: RichText? = null,
) : InputRichBlock

/** [InputRichBlockVideo](https://core.telegram.org/bots/api#inputrichblockvideo). */
data class InputRichBlockVideo(
    val type: String = "",
    val video: InputMediaVideo? = null,
    val caption: RichBlockCaption? = null,
) : InputRichBlock

/** [InputRichBlockVoiceNote](https://core.telegram.org/bots/api#inputrichblockvoicenote). */
data class InputRichBlockVoiceNote(
    val type: String = "",
    val voiceNote: InputMediaVoiceNote? = null,
    val caption: RichBlockCaption? = null,
) : InputRichBlock

/** [InputRichMessage](https://core.telegram.org/bots/api#inputrichmessage). */
data class InputRichMessage(
    val blocks: List<InputRichBlock>? = null,
    val html: String? = null,
    val markdown: String? = null,
    val media: List<InputRichMessageMedia>? = null,
    val isRtl: Boolean? = null,
    val skipEntityDetection: Boolean? = null,
)

/** [InputRichMessageContent](https://core.telegram.org/bots/api#inputrichmessagecontent). */
data class InputRichMessageContent(
    val richMessage: InputRichMessage? = null,
) : InputMessageContent

/** [InputRichMessageMedia](https://core.telegram.org/bots/api#inputrichmessagemedia). */
data class InputRichMessageMedia(
    val id: String = "",
    val media: MediaOfInputRichMessageMedia? = null,
)

/** [InputSticker](https://core.telegram.org/bots/api#inputsticker). */
data class InputSticker(
    val sticker: String = "",
    val format: String = "",
    val emojiList: List<String> = emptyList(),
    val maskPosition: MaskPosition? = null,
    val keywords: List<String>? = null,
)

/** [InputStoryContentPhoto](https://core.telegram.org/bots/api#inputstorycontentphoto). */
data class InputStoryContentPhoto(
    val type: String = "",
    val photo: String = "",
) : InputStoryContent

/** [InputStoryContentVideo](https://core.telegram.org/bots/api#inputstorycontentvideo). */
data class InputStoryContentVideo(
    val type: String = "",
    val video: String = "",
    val duration: Double? = null,
    val coverFrameTimestamp: Double? = null,
    val isAnimation: Boolean? = null,
) : InputStoryContent

/** [InputTextMessageContent](https://core.telegram.org/bots/api#inputtextmessagecontent). */
data class InputTextMessageContent(
    val messageText: String = "",
    val parseMode: String? = null,
    val entities: List<MessageEntity>? = null,
    val linkPreviewOptions: LinkPreviewOptions? = null,
) : InputMessageContent

/** [InputVenueMessageContent](https://core.telegram.org/bots/api#inputvenuemessagecontent). */
data class InputVenueMessageContent(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val title: String = "",
    val address: String = "",
    val foursquareId: String? = null,
    val foursquareType: String? = null,
    val googlePlaceId: String? = null,
    val googlePlaceType: String? = null,
) : InputMessageContent

/** [Invoice](https://core.telegram.org/bots/api#invoice). */
data class Invoice(
    val title: String = "",
    val description: String = "",
    val startParameter: String = "",
    val currency: String = "",
    val totalAmount: Long = 0,
)

/** [KeyboardButton](https://core.telegram.org/bots/api#keyboardbutton). */
data class KeyboardButton(
    val text: String = "",
    val iconCustomEmojiId: String? = null,
    val style: String? = null,
    val requestUsers: KeyboardButtonRequestUsers? = null,
    val requestChat: KeyboardButtonRequestChat? = null,
    val requestManagedBot: KeyboardButtonRequestManagedBot? = null,
    val requestContact: Boolean? = null,
    val requestLocation: Boolean? = null,
    val requestPoll: KeyboardButtonPollType? = null,
    val webApp: WebAppInfo? = null,
)

/** [KeyboardButtonPollType](https://core.telegram.org/bots/api#keyboardbuttonpolltype). */
data class KeyboardButtonPollType(
    val type: String? = null,
)

/** [KeyboardButtonRequestChat](https://core.telegram.org/bots/api#keyboardbuttonrequestchat). */
data class KeyboardButtonRequestChat(
    val requestId: Long = 0,
    val chatIsChannel: Boolean = false,
    val chatIsForum: Boolean? = null,
    val chatHasUsername: Boolean? = null,
    val chatIsCreated: Boolean? = null,
    val userAdministratorRights: ChatAdministratorRights? = null,
    val botAdministratorRights: ChatAdministratorRights? = null,
    val botIsMember: Boolean? = null,
    val requestTitle: Boolean? = null,
    val requestUsername: Boolean? = null,
    val requestPhoto: Boolean? = null,
)

/** [KeyboardButtonRequestManagedBot](https://core.telegram.org/bots/api#keyboardbuttonrequestmanagedbot). */
data class KeyboardButtonRequestManagedBot(
    val requestId: Long = 0,
    val suggestedName: String? = null,
    val suggestedUsername: String? = null,
)

/** [KeyboardButtonRequestUsers](https://core.telegram.org/bots/api#keyboardbuttonrequestusers). */
data class KeyboardButtonRequestUsers(
    val requestId: Long = 0,
    val userIsBot: Boolean? = null,
    val userIsPremium: Boolean? = null,
    val maxQuantity: Long? = null,
    val requestName: Boolean? = null,
    val requestUsername: Boolean? = null,
    val requestPhoto: Boolean? = null,
)

/** [LabeledPrice](https://core.telegram.org/bots/api#labeledprice). */
data class LabeledPrice(
    val label: String = "",
    val amount: Long = 0,
)

/** [Link](https://core.telegram.org/bots/api#link). */
data class Link(
    val url: String = "",
)

/** [LinkPreviewOptions](https://core.telegram.org/bots/api#linkpreviewoptions). */
data class LinkPreviewOptions(
    val isDisabled: Boolean? = null,
    val url: String? = null,
    val preferSmallMedia: Boolean? = null,
    val preferLargeMedia: Boolean? = null,
    val showAboveText: Boolean? = null,
)

/** [LivePhoto](https://core.telegram.org/bots/api#livephoto). */
data class LivePhoto(
    val photo: List<PhotoSize>? = null,
    val fileId: String = "",
    val fileUniqueId: String = "",
    val width: Long = 0,
    val height: Long = 0,
    val duration: Long = 0,
    val mimeType: String? = null,
    val fileSize: Long? = null,
)

/** [Location](https://core.telegram.org/bots/api#location). */
data class Location(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val horizontalAccuracy: Double? = null,
    val livePeriod: Long? = null,
    val heading: Long? = null,
    val proximityAlertRadius: Long? = null,
)

/** [LocationAddress](https://core.telegram.org/bots/api#locationaddress). */
data class LocationAddress(
    val countryCode: String = "",
    val state: String? = null,
    val city: String? = null,
    val street: String? = null,
)

/** [LoginUrl](https://core.telegram.org/bots/api#loginurl). */
data class LoginUrl(
    val url: String = "",
    val forwardText: String? = null,
    val botUsername: String? = null,
    val requestWriteAccess: Boolean? = null,
)

/** [ManagedBotCreated](https://core.telegram.org/bots/api#managedbotcreated). */
data class ManagedBotCreated(
    val bot: User? = null,
)

/** [ManagedBotUpdated](https://core.telegram.org/bots/api#managedbotupdated). */
data class ManagedBotUpdated(
    val user: User? = null,
    val bot: User? = null,
)

/** [MaskPosition](https://core.telegram.org/bots/api#maskposition). */
data class MaskPosition(
    val point: String = "",
    val xShift: Double = 0.0,
    val yShift: Double = 0.0,
    val scale: Double = 0.0,
)

/** [MenuButtonCommands](https://core.telegram.org/bots/api#menubuttoncommands). */
data class MenuButtonCommands(
    val type: String = "",
) : MenuButton

/** [MenuButtonDefault](https://core.telegram.org/bots/api#menubuttondefault). */
data class MenuButtonDefault(
    val type: String = "",
) : MenuButton

/** [MenuButtonWebApp](https://core.telegram.org/bots/api#menubuttonwebapp). */
data class MenuButtonWebApp(
    val type: String = "",
    val text: String = "",
    val webApp: WebAppInfo? = null,
) : MenuButton

/** [Message](https://core.telegram.org/bots/api#message). */
data class Message(
    val messageId: Long = 0,
    val messageThreadId: Long? = null,
    val directMessagesTopic: DirectMessagesTopic? = null,
    val from: User? = null,
    val senderChat: Chat? = null,
    val senderBoostCount: Long? = null,
    val senderBusinessBot: User? = null,
    val senderTag: String? = null,
    val receiverUser: User? = null,
    val ephemeralMessageId: Long? = null,
    val date: Long = 0,
    val guestQueryId: String? = null,
    val businessConnectionId: String? = null,
    val chat: Chat? = null,
    val forwardOrigin: MessageOrigin? = null,
    val isTopicMessage: Boolean? = null,
    val isAutomaticForward: Boolean? = null,
    val replyToMessage: Message? = null,
    val externalReply: ExternalReplyInfo? = null,
    val quote: TextQuote? = null,
    val replyToStory: Story? = null,
    val replyToChecklistTaskId: Long? = null,
    val replyToPollOptionId: String? = null,
    val viaBot: User? = null,
    val guestBotCallerUser: User? = null,
    val guestBotCallerChat: Chat? = null,
    val editDate: Long? = null,
    val hasProtectedContent: Boolean? = null,
    val isFromOffline: Boolean? = null,
    val isPaidPost: Boolean? = null,
    val mediaGroupId: String? = null,
    val authorSignature: String? = null,
    val paidStarCount: Long? = null,
    val text: String? = null,
    val entities: List<MessageEntity>? = null,
    val linkPreviewOptions: LinkPreviewOptions? = null,
    val suggestedPostInfo: SuggestedPostInfo? = null,
    val effectId: String? = null,
    val richMessage: RichMessage? = null,
    val animation: Animation? = null,
    val audio: Audio? = null,
    val document: Document? = null,
    val livePhoto: LivePhoto? = null,
    val paidMedia: PaidMediaInfo? = null,
    val photo: List<PhotoSize>? = null,
    val sticker: Sticker? = null,
    val story: Story? = null,
    val video: Video? = null,
    val videoNote: VideoNote? = null,
    val voice: Voice? = null,
    val caption: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val showCaptionAboveMedia: Boolean? = null,
    val hasMediaSpoiler: Boolean? = null,
    val checklist: Checklist? = null,
    val contact: Contact? = null,
    val dice: Dice? = null,
    val game: Game? = null,
    val poll: Poll? = null,
    val venue: Venue? = null,
    val location: Location? = null,
    val newChatMembers: List<User>? = null,
    val leftChatMember: User? = null,
    val chatOwnerLeft: ChatOwnerLeft? = null,
    val chatOwnerChanged: ChatOwnerChanged? = null,
    val newChatTitle: String? = null,
    val newChatPhoto: List<PhotoSize>? = null,
    val deleteChatPhoto: Boolean? = null,
    val groupChatCreated: Boolean? = null,
    val supergroupChatCreated: Boolean? = null,
    val channelChatCreated: Boolean? = null,
    val messageAutoDeleteTimerChanged: MessageAutoDeleteTimerChanged? = null,
    val migrateToChatId: Long? = null,
    val migrateFromChatId: Long? = null,
    val pinnedMessage: MaybeInaccessibleMessage? = null,
    val invoice: Invoice? = null,
    val successfulPayment: SuccessfulPayment? = null,
    val refundedPayment: RefundedPayment? = null,
    val usersShared: UsersShared? = null,
    val chatShared: ChatShared? = null,
    val gift: GiftInfo? = null,
    val uniqueGift: UniqueGiftInfo? = null,
    val giftUpgradeSent: GiftInfo? = null,
    val connectedWebsite: String? = null,
    val writeAccessAllowed: WriteAccessAllowed? = null,
    val passportData: PassportData? = null,
    val proximityAlertTriggered: ProximityAlertTriggered? = null,
    val boostAdded: ChatBoostAdded? = null,
    val chatBackgroundSet: ChatBackground? = null,
    val checklistTasksDone: ChecklistTasksDone? = null,
    val checklistTasksAdded: ChecklistTasksAdded? = null,
    val communityChatAdded: CommunityChatAdded? = null,
    val communityChatJoined: CommunityChatJoined? = null,
    val communityChatRemoved: CommunityChatRemoved? = null,
    val directMessagePriceChanged: DirectMessagePriceChanged? = null,
    val forumTopicCreated: ForumTopicCreated? = null,
    val forumTopicEdited: ForumTopicEdited? = null,
    val forumTopicClosed: ForumTopicClosed? = null,
    val forumTopicReopened: ForumTopicReopened? = null,
    val generalForumTopicHidden: GeneralForumTopicHidden? = null,
    val generalForumTopicUnhidden: GeneralForumTopicUnhidden? = null,
    val giveawayCreated: GiveawayCreated? = null,
    val giveaway: Giveaway? = null,
    val giveawayWinners: GiveawayWinners? = null,
    val giveawayCompleted: GiveawayCompleted? = null,
    val managedBotCreated: ManagedBotCreated? = null,
    val paidMessagePriceChanged: PaidMessagePriceChanged? = null,
    val pollOptionAdded: PollOptionAdded? = null,
    val pollOptionDeleted: PollOptionDeleted? = null,
    val suggestedPostApproved: SuggestedPostApproved? = null,
    val suggestedPostApprovalFailed: SuggestedPostApprovalFailed? = null,
    val suggestedPostDeclined: SuggestedPostDeclined? = null,
    val suggestedPostPaid: SuggestedPostPaid? = null,
    val suggestedPostRefunded: SuggestedPostRefunded? = null,
    val videoChatScheduled: VideoChatScheduled? = null,
    val videoChatStarted: VideoChatStarted? = null,
    val videoChatEnded: VideoChatEnded? = null,
    val videoChatParticipantsInvited: VideoChatParticipantsInvited? = null,
    val webAppData: WebAppData? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
) : MaybeInaccessibleMessage

/** [MessageAutoDeleteTimerChanged](https://core.telegram.org/bots/api#messageautodeletetimerchanged). */
data class MessageAutoDeleteTimerChanged(
    val messageAutoDeleteTime: Long = 0,
)

/** [MessageEntity](https://core.telegram.org/bots/api#messageentity). */
data class MessageEntity(
    val type: String = "",
    val offset: Long = 0,
    val length: Long = 0,
    val url: String? = null,
    val user: User? = null,
    val language: String? = null,
    val customEmojiId: String? = null,
    val unixTime: Long? = null,
    val dateTimeFormat: String? = null,
)

/** [MessageGenerationStopped](https://core.telegram.org/bots/api#messagegenerationstopped). */
data class MessageGenerationStopped(
    val chat: Chat? = null,
    val messageThreadId: Long? = null,
    val draftId: Long = 0,
)

/** [MessageId](https://core.telegram.org/bots/api#messageid). */
data class MessageId(
    val messageId: Long = 0,
)

/** [MessageOriginChannel](https://core.telegram.org/bots/api#messageoriginchannel). */
data class MessageOriginChannel(
    val type: String = "",
    val date: Long = 0,
    val chat: Chat? = null,
    val messageId: Long = 0,
    val authorSignature: String? = null,
) : MessageOrigin

/** [MessageOriginChat](https://core.telegram.org/bots/api#messageoriginchat). */
data class MessageOriginChat(
    val type: String = "",
    val date: Long = 0,
    val senderChat: Chat? = null,
    val authorSignature: String? = null,
) : MessageOrigin

/** [MessageOriginHiddenUser](https://core.telegram.org/bots/api#messageoriginhiddenuser). */
data class MessageOriginHiddenUser(
    val type: String = "",
    val date: Long = 0,
    val senderUserName: String = "",
) : MessageOrigin

/** [MessageOriginUser](https://core.telegram.org/bots/api#messageoriginuser). */
data class MessageOriginUser(
    val type: String = "",
    val date: Long = 0,
    val senderUser: User? = null,
) : MessageOrigin

/** [MessageReactionCountUpdated](https://core.telegram.org/bots/api#messagereactioncountupdated). */
data class MessageReactionCountUpdated(
    val chat: Chat? = null,
    val messageId: Long = 0,
    val date: Long = 0,
    val reactions: List<ReactionCount> = emptyList(),
)

/** [MessageReactionUpdated](https://core.telegram.org/bots/api#messagereactionupdated). */
data class MessageReactionUpdated(
    val chat: Chat? = null,
    val messageId: Long = 0,
    val user: User? = null,
    val actorChat: Chat? = null,
    val date: Long = 0,
    val oldReaction: List<ReactionType> = emptyList(),
    val newReaction: List<ReactionType> = emptyList(),
)

/** [OrderInfo](https://core.telegram.org/bots/api#orderinfo). */
data class OrderInfo(
    val name: String? = null,
    val phoneNumber: String? = null,
    val email: String? = null,
    val shippingAddress: ShippingAddress? = null,
)

/** [OwnedGiftRegular](https://core.telegram.org/bots/api#ownedgiftregular). */
data class OwnedGiftRegular(
    val type: String = "",
    val gift: Gift? = null,
    val ownedGiftId: String? = null,
    val senderUser: User? = null,
    val sendDate: Long = 0,
    val text: String? = null,
    val entities: List<MessageEntity>? = null,
    val isPrivate: Boolean? = null,
    val isSaved: Boolean? = null,
    val canBeUpgraded: Boolean? = null,
    val wasRefunded: Boolean? = null,
    val convertStarCount: Long? = null,
    val prepaidUpgradeStarCount: Long? = null,
    val isUpgradeSeparate: Boolean? = null,
    val uniqueGiftNumber: Long? = null,
) : OwnedGift

/** [OwnedGiftUnique](https://core.telegram.org/bots/api#ownedgiftunique). */
data class OwnedGiftUnique(
    val type: String = "",
    val gift: UniqueGift? = null,
    val ownedGiftId: String? = null,
    val senderUser: User? = null,
    val sendDate: Long = 0,
    val isSaved: Boolean? = null,
    val canBeTransferred: Boolean? = null,
    val transferStarCount: Long? = null,
    val nextTransferDate: Long? = null,
) : OwnedGift

/** [OwnedGifts](https://core.telegram.org/bots/api#ownedgifts). */
data class OwnedGifts(
    val totalCount: Long = 0,
    val gifts: List<OwnedGift> = emptyList(),
    val nextOffset: String? = null,
)

/** [PaidMediaInfo](https://core.telegram.org/bots/api#paidmediainfo). */
data class PaidMediaInfo(
    val starCount: Long = 0,
    val paidMedia: List<PaidMedia> = emptyList(),
)

/** [PaidMediaLivePhoto](https://core.telegram.org/bots/api#paidmedialivephoto). */
data class PaidMediaLivePhoto(
    val type: String = "",
    val livePhoto: LivePhoto? = null,
) : PaidMedia

/** [PaidMediaPhoto](https://core.telegram.org/bots/api#paidmediaphoto). */
data class PaidMediaPhoto(
    val type: String = "",
    val photo: List<PhotoSize> = emptyList(),
) : PaidMedia

/** [PaidMediaPreview](https://core.telegram.org/bots/api#paidmediapreview). */
data class PaidMediaPreview(
    val type: String = "",
    val width: Long? = null,
    val height: Long? = null,
    val duration: Long? = null,
) : PaidMedia

/** [PaidMediaPurchased](https://core.telegram.org/bots/api#paidmediapurchased). */
data class PaidMediaPurchased(
    val from: User? = null,
    val paidMediaPayload: String = "",
)

/** [PaidMediaVideo](https://core.telegram.org/bots/api#paidmediavideo). */
data class PaidMediaVideo(
    val type: String = "",
    val video: Video? = null,
) : PaidMedia

/** [PaidMessagePriceChanged](https://core.telegram.org/bots/api#paidmessagepricechanged). */
data class PaidMessagePriceChanged(
    val paidMessageStarCount: Long = 0,
)

/** [PassportData](https://core.telegram.org/bots/api#passportdata). */
data class PassportData(
    val data: List<EncryptedPassportElement> = emptyList(),
    val credentials: EncryptedCredentials? = null,
)

/** [PassportElementErrorDataField](https://core.telegram.org/bots/api#passportelementerrordatafield). */
data class PassportElementErrorDataField(
    val source: String = "",
    val type: String = "",
    val fieldName: String = "",
    val dataHash: String = "",
    val message: String = "",
) : PassportElementError

/** [PassportElementErrorFile](https://core.telegram.org/bots/api#passportelementerrorfile). */
data class PassportElementErrorFile(
    val source: String = "",
    val type: String = "",
    val fileHash: String = "",
    val message: String = "",
) : PassportElementError

/** [PassportElementErrorFiles](https://core.telegram.org/bots/api#passportelementerrorfiles). */
data class PassportElementErrorFiles(
    val source: String = "",
    val type: String = "",
    val fileHashes: List<String> = emptyList(),
    val message: String = "",
) : PassportElementError

/** [PassportElementErrorFrontSide](https://core.telegram.org/bots/api#passportelementerrorfrontside). */
data class PassportElementErrorFrontSide(
    val source: String = "",
    val type: String = "",
    val fileHash: String = "",
    val message: String = "",
) : PassportElementError

/** [PassportElementErrorReverseSide](https://core.telegram.org/bots/api#passportelementerrorreverseside). */
data class PassportElementErrorReverseSide(
    val source: String = "",
    val type: String = "",
    val fileHash: String = "",
    val message: String = "",
) : PassportElementError

/** [PassportElementErrorSelfie](https://core.telegram.org/bots/api#passportelementerrorselfie). */
data class PassportElementErrorSelfie(
    val source: String = "",
    val type: String = "",
    val fileHash: String = "",
    val message: String = "",
) : PassportElementError

/** [PassportElementErrorTranslationFile](https://core.telegram.org/bots/api#passportelementerrortranslationfile). */
data class PassportElementErrorTranslationFile(
    val source: String = "",
    val type: String = "",
    val fileHash: String = "",
    val message: String = "",
) : PassportElementError

/** [PassportElementErrorTranslationFiles](https://core.telegram.org/bots/api#passportelementerrortranslationfiles). */
data class PassportElementErrorTranslationFiles(
    val source: String = "",
    val type: String = "",
    val fileHashes: List<String> = emptyList(),
    val message: String = "",
) : PassportElementError

/** [PassportElementErrorUnspecified](https://core.telegram.org/bots/api#passportelementerrorunspecified). */
data class PassportElementErrorUnspecified(
    val source: String = "",
    val type: String = "",
    val elementHash: String = "",
    val message: String = "",
) : PassportElementError

/** [PassportFile](https://core.telegram.org/bots/api#passportfile). */
data class PassportFile(
    val fileId: String = "",
    val fileUniqueId: String = "",
    val fileSize: Long = 0,
    val fileDate: Long = 0,
)

/** [PhotoSize](https://core.telegram.org/bots/api#photosize). */
data class PhotoSize(
    val fileId: String = "",
    val fileUniqueId: String = "",
    val width: Long = 0,
    val height: Long = 0,
    val fileSize: Long? = null,
)

/** [Poll](https://core.telegram.org/bots/api#poll). */
data class Poll(
    val id: String = "",
    val question: String = "",
    val questionEntities: List<MessageEntity>? = null,
    val options: List<PollOption> = emptyList(),
    val totalVoterCount: Long = 0,
    val isClosed: Boolean = false,
    val isAnonymous: Boolean = false,
    val type: String = "",
    val allowsMultipleAnswers: Boolean = false,
    val allowsRevoting: Boolean = false,
    val membersOnly: Boolean = false,
    val countryCodes: List<String>? = null,
    val correctOptionIds: List<Long>? = null,
    val explanation: String? = null,
    val explanationEntities: List<MessageEntity>? = null,
    val explanationMedia: PollMedia? = null,
    val openPeriod: Long? = null,
    val closeDate: Long? = null,
    val description: String? = null,
    val descriptionEntities: List<MessageEntity>? = null,
    val media: PollMedia? = null,
)

/** [PollAnswer](https://core.telegram.org/bots/api#pollanswer). */
data class PollAnswer(
    val pollId: String = "",
    val voterChat: Chat? = null,
    val user: User? = null,
    val optionIds: List<Long> = emptyList(),
    val optionPersistentIds: List<String> = emptyList(),
)

/** [PollMedia](https://core.telegram.org/bots/api#pollmedia). */
data class PollMedia(
    val animation: Animation? = null,
    val audio: Audio? = null,
    val document: Document? = null,
    val link: Link? = null,
    val livePhoto: LivePhoto? = null,
    val location: Location? = null,
    val photo: List<PhotoSize>? = null,
    val sticker: Sticker? = null,
    val venue: Venue? = null,
    val video: Video? = null,
)

/** [PollOption](https://core.telegram.org/bots/api#polloption). */
data class PollOption(
    val persistentId: String = "",
    val text: String = "",
    val textEntities: List<MessageEntity>? = null,
    val media: PollMedia? = null,
    val voterCount: Long = 0,
    val addedByUser: User? = null,
    val addedByChat: Chat? = null,
    val additionDate: Long? = null,
)

/** [PollOptionAdded](https://core.telegram.org/bots/api#polloptionadded). */
data class PollOptionAdded(
    val pollMessage: MaybeInaccessibleMessage? = null,
    val optionPersistentId: String = "",
    val optionText: String = "",
    val optionTextEntities: List<MessageEntity>? = null,
)

/** [PollOptionDeleted](https://core.telegram.org/bots/api#polloptiondeleted). */
data class PollOptionDeleted(
    val pollMessage: MaybeInaccessibleMessage? = null,
    val optionPersistentId: String = "",
    val optionText: String = "",
    val optionTextEntities: List<MessageEntity>? = null,
)

/** [PreCheckoutQuery](https://core.telegram.org/bots/api#precheckoutquery). */
data class PreCheckoutQuery(
    val id: String = "",
    val from: User? = null,
    val currency: String = "",
    val totalAmount: Long = 0,
    val invoicePayload: String = "",
    val shippingOptionId: String? = null,
    val orderInfo: OrderInfo? = null,
)

/** [PreparedInlineMessage](https://core.telegram.org/bots/api#preparedinlinemessage). */
data class PreparedInlineMessage(
    val id: String = "",
    val expirationDate: Long = 0,
)

/** [PreparedKeyboardButton](https://core.telegram.org/bots/api#preparedkeyboardbutton). */
data class PreparedKeyboardButton(
    val id: String = "",
)

/** [ProximityAlertTriggered](https://core.telegram.org/bots/api#proximityalerttriggered). */
data class ProximityAlertTriggered(
    val traveler: User? = null,
    val watcher: User? = null,
    val distance: Long = 0,
)

/** [ReactionCount](https://core.telegram.org/bots/api#reactioncount). */
data class ReactionCount(
    val type: ReactionType? = null,
    val totalCount: Long = 0,
)

/** [ReactionTypeCustomEmoji](https://core.telegram.org/bots/api#reactiontypecustomemoji). */
data class ReactionTypeCustomEmoji(
    val type: String = "",
    val customEmojiId: String = "",
) : ReactionType

/** [ReactionTypeEmoji](https://core.telegram.org/bots/api#reactiontypeemoji). */
data class ReactionTypeEmoji(
    val type: String = "",
    val emoji: String = "",
) : ReactionType

/** [ReactionTypePaid](https://core.telegram.org/bots/api#reactiontypepaid). */
data class ReactionTypePaid(
    val type: String = "",
) : ReactionType

/** [RefundedPayment](https://core.telegram.org/bots/api#refundedpayment). */
data class RefundedPayment(
    val currency: String = "",
    val totalAmount: Long = 0,
    val invoicePayload: String = "",
    val telegramPaymentChargeId: String = "",
    val providerPaymentChargeId: String? = null,
)

/** [ReplyKeyboardMarkup](https://core.telegram.org/bots/api#replykeyboardmarkup). */
data class ReplyKeyboardMarkup(
    val keyboard: List<List<KeyboardButton>> = emptyList(),
    val isPersistent: Boolean? = null,
    val resizeKeyboard: Boolean? = null,
    val oneTimeKeyboard: Boolean? = null,
    val inputFieldPlaceholder: String? = null,
    val selective: Boolean? = null,
    val forceReply: Boolean? = null,
)

/** [ReplyKeyboardRemove](https://core.telegram.org/bots/api#replykeyboardremove). */
data class ReplyKeyboardRemove(
    val removeKeyboard: Boolean = false,
    val selective: Boolean? = null,
)

/** [ReplyParameters](https://core.telegram.org/bots/api#replyparameters). */
data class ReplyParameters(
    val messageId: Long? = null,
    val chatId: LongOrString? = null,
    val ephemeralMessageId: Long? = null,
    val allowSendingWithoutReply: Boolean? = null,
    val quote: String? = null,
    val quoteParseMode: String? = null,
    val quoteEntities: List<MessageEntity>? = null,
    val quotePosition: Long? = null,
    val checklistTaskId: Long? = null,
    val pollOptionId: String? = null,
)

/** [ResponseParameters](https://core.telegram.org/bots/api#responseparameters). */
data class ResponseParameters(
    val migrateToChatId: Long? = null,
    val retryAfter: Long? = null,
)

/** [RevenueWithdrawalStateFailed](https://core.telegram.org/bots/api#revenuewithdrawalstatefailed). */
data class RevenueWithdrawalStateFailed(
    val type: String = "",
) : RevenueWithdrawalState

/** [RevenueWithdrawalStatePending](https://core.telegram.org/bots/api#revenuewithdrawalstatepending). */
data class RevenueWithdrawalStatePending(
    val type: String = "",
) : RevenueWithdrawalState

/** [RevenueWithdrawalStateSucceeded](https://core.telegram.org/bots/api#revenuewithdrawalstatesucceeded). */
data class RevenueWithdrawalStateSucceeded(
    val type: String = "",
    val date: Long = 0,
    val url: String = "",
) : RevenueWithdrawalState

/** [RichBlockAnchor](https://core.telegram.org/bots/api#richblockanchor). */
data class RichBlockAnchor(
    val type: String = "",
    val name: String = "",
) : RichBlock

/** [RichBlockAnimation](https://core.telegram.org/bots/api#richblockanimation). */
data class RichBlockAnimation(
    val type: String = "",
    val animation: Animation? = null,
    val hasSpoiler: Boolean? = null,
    val caption: RichBlockCaption? = null,
) : RichBlock

/** [RichBlockAudio](https://core.telegram.org/bots/api#richblockaudio). */
data class RichBlockAudio(
    val type: String = "",
    val audio: Audio? = null,
    val caption: RichBlockCaption? = null,
) : RichBlock

/** [RichBlockBlockQuotation](https://core.telegram.org/bots/api#richblockblockquotation). */
data class RichBlockBlockQuotation(
    val type: String = "",
    val blocks: List<RichBlock> = emptyList(),
    val credit: RichText? = null,
) : RichBlock

/** [RichBlockButtons](https://core.telegram.org/bots/api#richblockbuttons). */
data class RichBlockButtons(
    val type: String = "",
    val buttons: List<RichMessageButton> = emptyList(),
    val align: String? = null,
) : RichBlock

/** [RichBlockCaption](https://core.telegram.org/bots/api#richblockcaption). */
data class RichBlockCaption(
    val text: RichText? = null,
    val credit: RichText? = null,
)

/** [RichBlockCollage](https://core.telegram.org/bots/api#richblockcollage). */
data class RichBlockCollage(
    val type: String = "",
    val blocks: List<RichBlock> = emptyList(),
    val caption: RichBlockCaption? = null,
) : RichBlock

/** [RichBlockDetails](https://core.telegram.org/bots/api#richblockdetails). */
data class RichBlockDetails(
    val type: String = "",
    val summary: RichText? = null,
    val blocks: List<RichBlock> = emptyList(),
    val isOpen: Boolean? = null,
) : RichBlock

/** [RichBlockDivider](https://core.telegram.org/bots/api#richblockdivider). */
data class RichBlockDivider(
    val type: String = "",
) : RichBlock

/** [RichBlockDocument](https://core.telegram.org/bots/api#richblockdocument). */
data class RichBlockDocument(
    val type: String = "",
    val document: Document? = null,
    val caption: RichBlockCaption? = null,
) : RichBlock

/** [RichBlockExpandableBlockQuotation](https://core.telegram.org/bots/api#richblockexpandableblockquotation). */
data class RichBlockExpandableBlockQuotation(
    val type: String = "",
    val text: RichText? = null,
    val credit: RichText? = null,
) : RichBlock

/** [RichBlockFooter](https://core.telegram.org/bots/api#richblockfooter). */
data class RichBlockFooter(
    val type: String = "",
    val text: RichText? = null,
) : RichBlock

/** [RichBlockList](https://core.telegram.org/bots/api#richblocklist). */
data class RichBlockList(
    val type: String = "",
    val items: List<RichBlockListItem> = emptyList(),
) : RichBlock

/** [RichBlockListItem](https://core.telegram.org/bots/api#richblocklistitem). */
data class RichBlockListItem(
    val label: String = "",
    val blocks: List<RichBlock> = emptyList(),
    val hasCheckbox: Boolean? = null,
    val isChecked: Boolean? = null,
    val value: Long? = null,
    val type: String? = null,
)

/** [RichBlockMap](https://core.telegram.org/bots/api#richblockmap). */
data class RichBlockMap(
    val type: String = "",
    val location: Location? = null,
    val zoom: Long = 0,
    val width: Long = 0,
    val height: Long = 0,
    val caption: RichBlockCaption? = null,
) : RichBlock

/** [RichBlockMathematicalExpression](https://core.telegram.org/bots/api#richblockmathematicalexpression). */
data class RichBlockMathematicalExpression(
    val type: String = "",
    val expression: String = "",
) : RichBlock

/** [RichBlockParagraph](https://core.telegram.org/bots/api#richblockparagraph). */
data class RichBlockParagraph(
    val type: String = "",
    val text: RichText? = null,
) : RichBlock

/** [RichBlockPhoto](https://core.telegram.org/bots/api#richblockphoto). */
data class RichBlockPhoto(
    val type: String = "",
    val photo: List<PhotoSize> = emptyList(),
    val hasSpoiler: Boolean? = null,
    val caption: RichBlockCaption? = null,
) : RichBlock

/** [RichBlockPreformatted](https://core.telegram.org/bots/api#richblockpreformatted). */
data class RichBlockPreformatted(
    val type: String = "",
    val text: RichText? = null,
    val language: String? = null,
) : RichBlock

/** [RichBlockPullQuotation](https://core.telegram.org/bots/api#richblockpullquotation). */
data class RichBlockPullQuotation(
    val type: String = "",
    val text: RichText? = null,
    val credit: RichText? = null,
) : RichBlock

/** [RichBlockSectionHeading](https://core.telegram.org/bots/api#richblocksectionheading). */
data class RichBlockSectionHeading(
    val type: String = "",
    val text: RichText? = null,
    val size: Long = 0,
) : RichBlock

/** [RichBlockSlideshow](https://core.telegram.org/bots/api#richblockslideshow). */
data class RichBlockSlideshow(
    val type: String = "",
    val blocks: List<RichBlock> = emptyList(),
    val caption: RichBlockCaption? = null,
) : RichBlock

/** [RichBlockTable](https://core.telegram.org/bots/api#richblocktable). */
data class RichBlockTable(
    val type: String = "",
    val cells: List<List<RichBlockTableCell>> = emptyList(),
    val isBordered: Boolean? = null,
    val isStriped: Boolean? = null,
    val isCompact: Boolean? = null,
    val caption: RichText? = null,
) : RichBlock

/** [RichBlockTableCell](https://core.telegram.org/bots/api#richblocktablecell). */
data class RichBlockTableCell(
    val text: RichText? = null,
    val isHeader: Boolean? = null,
    val colspan: Long? = null,
    val rowspan: Long? = null,
    val align: String = "",
    val valign: String = "",
)

/** [RichBlockThinking](https://core.telegram.org/bots/api#richblockthinking). */
data class RichBlockThinking(
    val type: String = "",
    val text: RichText? = null,
) : RichBlock

/** [RichBlockVideo](https://core.telegram.org/bots/api#richblockvideo). */
data class RichBlockVideo(
    val type: String = "",
    val video: Video? = null,
    val hasSpoiler: Boolean? = null,
    val caption: RichBlockCaption? = null,
) : RichBlock

/** [RichBlockVoiceNote](https://core.telegram.org/bots/api#richblockvoicenote). */
data class RichBlockVoiceNote(
    val type: String = "",
    val voiceNote: Voice? = null,
    val caption: RichBlockCaption? = null,
) : RichBlock

/** [RichMessage](https://core.telegram.org/bots/api#richmessage). */
data class RichMessage(
    val blocks: List<RichBlock> = emptyList(),
    val isRtl: Boolean? = null,
)

/** [RichMessageButton](https://core.telegram.org/bots/api#richmessagebutton). */
data class RichMessageButton(
    val text: RichText? = null,
    val style: String? = null,
    val url: String? = null,
    val callbackData: String? = null,
    val webApp: WebAppInfo? = null,
    val loginUrl: LoginUrl? = null,
    val switchInlineQuery: String? = null,
    val switchInlineQueryCurrentChat: String? = null,
    val switchInlineQueryChosenChat: SwitchInlineQueryChosenChat? = null,
    val copyText: CopyTextButton? = null,
    val disabled: DisabledButton? = null,
)

/** [RichTextAnchor](https://core.telegram.org/bots/api#richtextanchor). */
data class RichTextAnchor(
    val type: String = "",
    val name: String = "",
) : RichText

/** [RichTextAnchorLink](https://core.telegram.org/bots/api#richtextanchorlink). */
data class RichTextAnchorLink(
    val type: String = "",
    val text: RichText? = null,
    val anchorName: String = "",
) : RichText

/** [RichTextBankCardNumber](https://core.telegram.org/bots/api#richtextbankcardnumber). */
data class RichTextBankCardNumber(
    val type: String = "",
    val text: RichText? = null,
    val bankCardNumber: String = "",
) : RichText

/** [RichTextBold](https://core.telegram.org/bots/api#richtextbold). */
data class RichTextBold(
    val type: String = "",
    val text: RichText? = null,
) : RichText

/** [RichTextBotCommand](https://core.telegram.org/bots/api#richtextbotcommand). */
data class RichTextBotCommand(
    val type: String = "",
    val text: RichText? = null,
    val botCommand: String = "",
) : RichText

/** [RichTextButton](https://core.telegram.org/bots/api#richtextbutton). */
data class RichTextButton(
    val type: String = "",
    val button: RichMessageButton? = null,
) : RichText

/** [RichTextCashtag](https://core.telegram.org/bots/api#richtextcashtag). */
data class RichTextCashtag(
    val type: String = "",
    val text: RichText? = null,
    val cashtag: String = "",
) : RichText

/** [RichTextCode](https://core.telegram.org/bots/api#richtextcode). */
data class RichTextCode(
    val type: String = "",
    val text: RichText? = null,
) : RichText

/** [RichTextCustomEmoji](https://core.telegram.org/bots/api#richtextcustomemoji). */
data class RichTextCustomEmoji(
    val type: String = "",
    val customEmojiId: String = "",
    val alternativeText: String = "",
) : RichText

/** [RichTextDateTime](https://core.telegram.org/bots/api#richtextdatetime). */
data class RichTextDateTime(
    val type: String = "",
    val text: RichText? = null,
    val unixTime: Long = 0,
    val dateTimeFormat: String = "",
) : RichText

/** [RichTextEmailAddress](https://core.telegram.org/bots/api#richtextemailaddress). */
data class RichTextEmailAddress(
    val type: String = "",
    val text: RichText? = null,
    val emailAddress: String = "",
) : RichText

/** [RichTextHashtag](https://core.telegram.org/bots/api#richtexthashtag). */
data class RichTextHashtag(
    val type: String = "",
    val text: RichText? = null,
    val hashtag: String = "",
) : RichText

/** [RichTextItalic](https://core.telegram.org/bots/api#richtextitalic). */
data class RichTextItalic(
    val type: String = "",
    val text: RichText? = null,
) : RichText

/** [RichTextMarked](https://core.telegram.org/bots/api#richtextmarked). */
data class RichTextMarked(
    val type: String = "",
    val text: RichText? = null,
) : RichText

/** [RichTextMathematicalExpression](https://core.telegram.org/bots/api#richtextmathematicalexpression). */
data class RichTextMathematicalExpression(
    val type: String = "",
    val expression: String = "",
) : RichText

/** [RichTextMention](https://core.telegram.org/bots/api#richtextmention). */
data class RichTextMention(
    val type: String = "",
    val text: RichText? = null,
    val username: String = "",
) : RichText

/** [RichTextPhoneNumber](https://core.telegram.org/bots/api#richtextphonenumber). */
data class RichTextPhoneNumber(
    val type: String = "",
    val text: RichText? = null,
    val phoneNumber: String = "",
) : RichText

/** [RichTextReference](https://core.telegram.org/bots/api#richtextreference). */
data class RichTextReference(
    val type: String = "",
    val text: RichText? = null,
    val name: String = "",
) : RichText

/** [RichTextReferenceLink](https://core.telegram.org/bots/api#richtextreferencelink). */
data class RichTextReferenceLink(
    val type: String = "",
    val text: RichText? = null,
    val referenceName: String = "",
) : RichText

/** [RichTextSpoiler](https://core.telegram.org/bots/api#richtextspoiler). */
data class RichTextSpoiler(
    val type: String = "",
    val text: RichText? = null,
) : RichText

/** [RichTextStrikethrough](https://core.telegram.org/bots/api#richtextstrikethrough). */
data class RichTextStrikethrough(
    val type: String = "",
    val text: RichText? = null,
) : RichText

/** [RichTextSubscript](https://core.telegram.org/bots/api#richtextsubscript). */
data class RichTextSubscript(
    val type: String = "",
    val text: RichText? = null,
) : RichText

/** [RichTextSuperscript](https://core.telegram.org/bots/api#richtextsuperscript). */
data class RichTextSuperscript(
    val type: String = "",
    val text: RichText? = null,
) : RichText

/** [RichTextTextMention](https://core.telegram.org/bots/api#richtexttextmention). */
data class RichTextTextMention(
    val type: String = "",
    val text: RichText? = null,
    val user: User? = null,
) : RichText

/** [RichTextUnderline](https://core.telegram.org/bots/api#richtextunderline). */
data class RichTextUnderline(
    val type: String = "",
    val text: RichText? = null,
) : RichText

/** [RichTextUrl](https://core.telegram.org/bots/api#richtexturl). */
data class RichTextUrl(
    val type: String = "",
    val text: RichText? = null,
    val url: String = "",
) : RichText

/** [SentGuestMessage](https://core.telegram.org/bots/api#sentguestmessage). */
data class SentGuestMessage(
    val inlineMessageId: String = "",
)

/** [SentWebAppMessage](https://core.telegram.org/bots/api#sentwebappmessage). */
data class SentWebAppMessage(
    val inlineMessageId: String? = null,
)

/** [SharedUser](https://core.telegram.org/bots/api#shareduser). */
data class SharedUser(
    val userId: Long = 0,
    val firstName: String? = null,
    val lastName: String? = null,
    val username: String? = null,
    val photo: List<PhotoSize>? = null,
)

/** [ShippingAddress](https://core.telegram.org/bots/api#shippingaddress). */
data class ShippingAddress(
    val countryCode: String = "",
    val state: String = "",
    val city: String = "",
    val streetLine1: String = "",
    val streetLine2: String = "",
    val postCode: String = "",
)

/** [ShippingOption](https://core.telegram.org/bots/api#shippingoption). */
data class ShippingOption(
    val id: String = "",
    val title: String = "",
    val prices: List<LabeledPrice> = emptyList(),
)

/** [ShippingQuery](https://core.telegram.org/bots/api#shippingquery). */
data class ShippingQuery(
    val id: String = "",
    val from: User? = null,
    val invoicePayload: String = "",
    val shippingAddress: ShippingAddress? = null,
)

/** [StarAmount](https://core.telegram.org/bots/api#staramount). */
data class StarAmount(
    val amount: Long = 0,
    val nanostarAmount: Long? = null,
)

/** [StarTransaction](https://core.telegram.org/bots/api#startransaction). */
data class StarTransaction(
    val id: String = "",
    val amount: Long = 0,
    val nanostarAmount: Long? = null,
    val date: Long = 0,
    val source: TransactionPartner? = null,
    val receiver: TransactionPartner? = null,
)

/** [StarTransactions](https://core.telegram.org/bots/api#startransactions). */
data class StarTransactions(
    val transactions: List<StarTransaction> = emptyList(),
)

/** [Sticker](https://core.telegram.org/bots/api#sticker). */
data class Sticker(
    val fileId: String = "",
    val fileUniqueId: String = "",
    val type: String = "",
    val width: Long = 0,
    val height: Long = 0,
    val isAnimated: Boolean = false,
    val isVideo: Boolean = false,
    val thumbnail: PhotoSize? = null,
    val emoji: String? = null,
    val setName: String? = null,
    val premiumAnimation: File? = null,
    val maskPosition: MaskPosition? = null,
    val customEmojiId: String? = null,
    val needsRepainting: Boolean? = null,
    val fileSize: Long? = null,
)

/** [StickerSet](https://core.telegram.org/bots/api#stickerset). */
data class StickerSet(
    val name: String = "",
    val title: String = "",
    val stickerType: String = "",
    val stickers: List<Sticker> = emptyList(),
    val thumbnail: PhotoSize? = null,
)

/** [Story](https://core.telegram.org/bots/api#story). */
data class Story(
    val chat: Chat? = null,
    val id: Long = 0,
)

/** [StoryArea](https://core.telegram.org/bots/api#storyarea). */
data class StoryArea(
    val position: StoryAreaPosition? = null,
    val type: StoryAreaType? = null,
)

/** [StoryAreaPosition](https://core.telegram.org/bots/api#storyareaposition). */
data class StoryAreaPosition(
    val xPercentage: Double = 0.0,
    val yPercentage: Double = 0.0,
    val widthPercentage: Double = 0.0,
    val heightPercentage: Double = 0.0,
    val rotationAngle: Double = 0.0,
    val cornerRadiusPercentage: Double = 0.0,
)

/** [StoryAreaTypeLink](https://core.telegram.org/bots/api#storyareatypelink). */
data class StoryAreaTypeLink(
    val type: String = "",
    val url: String = "",
) : StoryAreaType

/** [StoryAreaTypeLocation](https://core.telegram.org/bots/api#storyareatypelocation). */
data class StoryAreaTypeLocation(
    val type: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: LocationAddress? = null,
) : StoryAreaType

/** [StoryAreaTypeSuggestedReaction](https://core.telegram.org/bots/api#storyareatypesuggestedreaction). */
data class StoryAreaTypeSuggestedReaction(
    val type: String = "",
    val reactionType: ReactionType? = null,
    val isDark: Boolean? = null,
    val isFlipped: Boolean? = null,
) : StoryAreaType

/** [StoryAreaTypeUniqueGift](https://core.telegram.org/bots/api#storyareatypeuniquegift). */
data class StoryAreaTypeUniqueGift(
    val type: String = "",
    val name: String = "",
) : StoryAreaType

/** [StoryAreaTypeWeather](https://core.telegram.org/bots/api#storyareatypeweather). */
data class StoryAreaTypeWeather(
    val type: String = "",
    val temperature: Double = 0.0,
    val emoji: String = "",
    val backgroundColor: Long = 0,
) : StoryAreaType

/** [SuccessfulPayment](https://core.telegram.org/bots/api#successfulpayment). */
data class SuccessfulPayment(
    val currency: String = "",
    val totalAmount: Long = 0,
    val invoicePayload: String = "",
    val subscriptionExpirationDate: Long? = null,
    val isRecurring: Boolean? = null,
    val isFirstRecurring: Boolean? = null,
    val shippingOptionId: String? = null,
    val orderInfo: OrderInfo? = null,
    val telegramPaymentChargeId: String = "",
    val providerPaymentChargeId: String = "",
)

/** [SuggestedPostApprovalFailed](https://core.telegram.org/bots/api#suggestedpostapprovalfailed). */
data class SuggestedPostApprovalFailed(
    val suggestedPostMessage: Message? = null,
    val price: SuggestedPostPrice? = null,
)

/** [SuggestedPostApproved](https://core.telegram.org/bots/api#suggestedpostapproved). */
data class SuggestedPostApproved(
    val suggestedPostMessage: Message? = null,
    val price: SuggestedPostPrice? = null,
    val sendDate: Long = 0,
)

/** [SuggestedPostDeclined](https://core.telegram.org/bots/api#suggestedpostdeclined). */
data class SuggestedPostDeclined(
    val suggestedPostMessage: Message? = null,
    val comment: String? = null,
)

/** [SuggestedPostInfo](https://core.telegram.org/bots/api#suggestedpostinfo). */
data class SuggestedPostInfo(
    val state: String = "",
    val price: SuggestedPostPrice? = null,
    val sendDate: Long? = null,
)

/** [SuggestedPostPaid](https://core.telegram.org/bots/api#suggestedpostpaid). */
data class SuggestedPostPaid(
    val suggestedPostMessage: Message? = null,
    val currency: String = "",
    val amount: Long? = null,
    val starAmount: StarAmount? = null,
)

/** [SuggestedPostParameters](https://core.telegram.org/bots/api#suggestedpostparameters). */
data class SuggestedPostParameters(
    val price: SuggestedPostPrice? = null,
    val sendDate: Long? = null,
)

/** [SuggestedPostPrice](https://core.telegram.org/bots/api#suggestedpostprice). */
data class SuggestedPostPrice(
    val currency: String = "",
    val amount: Long = 0,
)

/** [SuggestedPostRefunded](https://core.telegram.org/bots/api#suggestedpostrefunded). */
data class SuggestedPostRefunded(
    val suggestedPostMessage: Message? = null,
    val reason: String = "",
)

/** [SwitchInlineQueryChosenChat](https://core.telegram.org/bots/api#switchinlinequerychosenchat). */
data class SwitchInlineQueryChosenChat(
    val query: String? = null,
    val allowUserChats: Boolean? = null,
    val allowBotChats: Boolean? = null,
    val allowGroupChats: Boolean? = null,
    val allowChannelChats: Boolean? = null,
)

/** [TextQuote](https://core.telegram.org/bots/api#textquote). */
data class TextQuote(
    val text: String = "",
    val entities: List<MessageEntity>? = null,
    val position: Long = 0,
    val isManual: Boolean? = null,
)

/** [TransactionPartnerAffiliateProgram](https://core.telegram.org/bots/api#transactionpartneraffiliateprogram). */
data class TransactionPartnerAffiliateProgram(
    val type: String = "",
    val sponsorUser: User? = null,
    val commissionPerMille: Long = 0,
) : TransactionPartner

/** [TransactionPartnerChat](https://core.telegram.org/bots/api#transactionpartnerchat). */
data class TransactionPartnerChat(
    val type: String = "",
    val chat: Chat? = null,
    val gift: Gift? = null,
) : TransactionPartner

/** [TransactionPartnerFragment](https://core.telegram.org/bots/api#transactionpartnerfragment). */
data class TransactionPartnerFragment(
    val type: String = "",
    val withdrawalState: RevenueWithdrawalState? = null,
) : TransactionPartner

/** [TransactionPartnerOther](https://core.telegram.org/bots/api#transactionpartnerother). */
data class TransactionPartnerOther(
    val type: String = "",
) : TransactionPartner

/** [TransactionPartnerTelegramAds](https://core.telegram.org/bots/api#transactionpartnertelegramads). */
data class TransactionPartnerTelegramAds(
    val type: String = "",
) : TransactionPartner

/** [TransactionPartnerTelegramApi](https://core.telegram.org/bots/api#transactionpartnertelegramapi). */
data class TransactionPartnerTelegramApi(
    val type: String = "",
    val requestCount: Long = 0,
) : TransactionPartner

/** [TransactionPartnerUser](https://core.telegram.org/bots/api#transactionpartneruser). */
data class TransactionPartnerUser(
    val type: String = "",
    val transactionType: String = "",
    val user: User? = null,
    val affiliate: AffiliateInfo? = null,
    val invoicePayload: String? = null,
    val subscriptionPeriod: Long? = null,
    val paidMedia: List<PaidMedia>? = null,
    val paidMediaPayload: String? = null,
    val gift: Gift? = null,
    val premiumSubscriptionDuration: Long? = null,
) : TransactionPartner

/** [UniqueGift](https://core.telegram.org/bots/api#uniquegift). */
data class UniqueGift(
    val giftId: String = "",
    val baseName: String = "",
    val name: String = "",
    val number: Long = 0,
    val model: UniqueGiftModel? = null,
    val symbol: UniqueGiftSymbol? = null,
    val backdrop: UniqueGiftBackdrop? = null,
    val isPremium: Boolean? = null,
    val isBurned: Boolean? = null,
    val isFromBlockchain: Boolean? = null,
    val colors: UniqueGiftColors? = null,
    val publisherChat: Chat? = null,
)

/** [UniqueGiftBackdrop](https://core.telegram.org/bots/api#uniquegiftbackdrop). */
data class UniqueGiftBackdrop(
    val name: String = "",
    val colors: UniqueGiftBackdropColors? = null,
    val rarityPerMille: Long = 0,
)

/** [UniqueGiftBackdropColors](https://core.telegram.org/bots/api#uniquegiftbackdropcolors). */
data class UniqueGiftBackdropColors(
    val centerColor: Long = 0,
    val edgeColor: Long = 0,
    val symbolColor: Long = 0,
    val textColor: Long = 0,
)

/** [UniqueGiftColors](https://core.telegram.org/bots/api#uniquegiftcolors). */
data class UniqueGiftColors(
    val modelCustomEmojiId: String = "",
    val symbolCustomEmojiId: String = "",
    val lightThemeMainColor: Long = 0,
    val lightThemeOtherColors: List<Long> = emptyList(),
    val darkThemeMainColor: Long = 0,
    val darkThemeOtherColors: List<Long> = emptyList(),
)

/** [UniqueGiftInfo](https://core.telegram.org/bots/api#uniquegiftinfo). */
data class UniqueGiftInfo(
    val gift: UniqueGift? = null,
    val origin: String = "",
    val text: String? = null,
    val entities: List<MessageEntity>? = null,
    val isPrivate: Boolean? = null,
    val lastResaleCurrency: String? = null,
    val lastResaleAmount: Long? = null,
    val ownedGiftId: String? = null,
    val transferStarCount: Long? = null,
    val nextTransferDate: Long? = null,
)

/** [UniqueGiftModel](https://core.telegram.org/bots/api#uniquegiftmodel). */
data class UniqueGiftModel(
    val name: String = "",
    val sticker: Sticker? = null,
    val rarityPerMille: Long = 0,
    val rarity: String? = null,
)

/** [UniqueGiftSymbol](https://core.telegram.org/bots/api#uniquegiftsymbol). */
data class UniqueGiftSymbol(
    val name: String = "",
    val sticker: Sticker? = null,
    val rarityPerMille: Long = 0,
)

/** [Update](https://core.telegram.org/bots/api#update). */
data class Update(
    val updateId: Long = 0,
    val message: Message? = null,
    val editedMessage: Message? = null,
    val channelPost: Message? = null,
    val editedChannelPost: Message? = null,
    val businessConnection: BusinessConnection? = null,
    val businessMessage: Message? = null,
    val editedBusinessMessage: Message? = null,
    val deletedBusinessMessages: BusinessMessagesDeleted? = null,
    val guestMessage: Message? = null,
    val messageReaction: MessageReactionUpdated? = null,
    val messageReactionCount: MessageReactionCountUpdated? = null,
    val inlineQuery: InlineQuery? = null,
    val chosenInlineResult: ChosenInlineResult? = null,
    val callbackQuery: CallbackQuery? = null,
    val shippingQuery: ShippingQuery? = null,
    val preCheckoutQuery: PreCheckoutQuery? = null,
    val purchasedPaidMedia: PaidMediaPurchased? = null,
    val poll: Poll? = null,
    val pollAnswer: PollAnswer? = null,
    val myChatMember: ChatMemberUpdated? = null,
    val chatMember: ChatMemberUpdated? = null,
    val chatJoinRequest: ChatJoinRequest? = null,
    val chatBoost: ChatBoostUpdated? = null,
    val removedChatBoost: ChatBoostRemoved? = null,
    val managedBot: ManagedBotUpdated? = null,
    val subscription: BotSubscriptionUpdated? = null,
    val stoppedMessageGeneration: MessageGenerationStopped? = null,
)

/** [User](https://core.telegram.org/bots/api#user). */
data class User(
    val id: Long = 0,
    val isBot: Boolean = false,
    val firstName: String = "",
    val lastName: String? = null,
    val username: String? = null,
    val languageCode: String? = null,
    val isPremium: Boolean? = null,
    val addedToAttachmentMenu: Boolean? = null,
    val canJoinGroups: Boolean? = null,
    val canReadAllGroupMessages: Boolean? = null,
    val supportsGuestQueries: Boolean? = null,
    val supportsInlineQueries: Boolean? = null,
    val canConnectToBusiness: Boolean? = null,
    val hasMainWebApp: Boolean? = null,
    val hasTopicsEnabled: Boolean? = null,
    val allowsUsersToCreateTopics: Boolean? = null,
    val canManageBots: Boolean? = null,
    val supportsJoinRequestQueries: Boolean? = null,
)

/** [UserChatBoosts](https://core.telegram.org/bots/api#userchatboosts). */
data class UserChatBoosts(
    val boosts: List<ChatBoost> = emptyList(),
)

/** [UserProfileAudios](https://core.telegram.org/bots/api#userprofileaudios). */
data class UserProfileAudios(
    val totalCount: Long = 0,
    val audios: List<Audio> = emptyList(),
)

/** [UserProfilePhotos](https://core.telegram.org/bots/api#userprofilephotos). */
data class UserProfilePhotos(
    val totalCount: Long = 0,
    val photos: List<List<PhotoSize>> = emptyList(),
)

/** [UserRating](https://core.telegram.org/bots/api#userrating). */
data class UserRating(
    val level: Long = 0,
    val rating: Long = 0,
    val currentLevelRating: Long = 0,
    val nextLevelRating: Long? = null,
)

/** [UsersShared](https://core.telegram.org/bots/api#usersshared). */
data class UsersShared(
    val requestId: Long = 0,
    val users: List<SharedUser> = emptyList(),
)

/** [Venue](https://core.telegram.org/bots/api#venue). */
data class Venue(
    val location: Location? = null,
    val title: String = "",
    val address: String = "",
    val foursquareId: String? = null,
    val foursquareType: String? = null,
    val googlePlaceId: String? = null,
    val googlePlaceType: String? = null,
)

/** [Video](https://core.telegram.org/bots/api#video). */
data class Video(
    val fileId: String = "",
    val fileUniqueId: String = "",
    val width: Long = 0,
    val height: Long = 0,
    val duration: Long = 0,
    val thumbnail: PhotoSize? = null,
    val cover: List<PhotoSize>? = null,
    val startTimestamp: Long? = null,
    val qualities: List<VideoQuality>? = null,
    val fileName: String? = null,
    val mimeType: String? = null,
    val fileSize: Long? = null,
)

/** [VideoChatEnded](https://core.telegram.org/bots/api#videochatended). */
data class VideoChatEnded(
    val duration: Long = 0,
)

/** [VideoChatParticipantsInvited](https://core.telegram.org/bots/api#videochatparticipantsinvited). */
data class VideoChatParticipantsInvited(
    val users: List<User> = emptyList(),
)

/** [VideoChatScheduled](https://core.telegram.org/bots/api#videochatscheduled). */
data class VideoChatScheduled(
    val startDate: Long = 0,
)

/** [VideoChatStarted](https://core.telegram.org/bots/api#videochatstarted). */
data object VideoChatStarted

/** [VideoNote](https://core.telegram.org/bots/api#videonote). */
data class VideoNote(
    val fileId: String = "",
    val fileUniqueId: String = "",
    val length: Long = 0,
    val duration: Long = 0,
    val thumbnail: PhotoSize? = null,
    val fileSize: Long? = null,
)

/** [VideoQuality](https://core.telegram.org/bots/api#videoquality). */
data class VideoQuality(
    val fileId: String = "",
    val fileUniqueId: String = "",
    val width: Long = 0,
    val height: Long = 0,
    val codec: String = "",
    val fileSize: Long? = null,
)

/** [Voice](https://core.telegram.org/bots/api#voice). */
data class Voice(
    val fileId: String = "",
    val fileUniqueId: String = "",
    val duration: Long = 0,
    val mimeType: String? = null,
    val fileSize: Long? = null,
)

/** [WebAppData](https://core.telegram.org/bots/api#webappdata). */
data class WebAppData(
    val data: String = "",
    val buttonText: String = "",
)

/** [WebAppInfo](https://core.telegram.org/bots/api#webappinfo). */
data class WebAppInfo(
    val url: String = "",
)

/** [WebhookInfo](https://core.telegram.org/bots/api#webhookinfo). */
data class WebhookInfo(
    val url: String = "",
    val hasCustomCertificate: Boolean = false,
    val pendingUpdateCount: Long = 0,
    val ipAddress: String? = null,
    val lastErrorDate: Long? = null,
    val lastErrorMessage: String? = null,
    val lastSynchronizationErrorDate: Long? = null,
    val maxConnections: Long? = null,
    val allowedUpdates: List<String>? = null,
)

/** [WriteAccessAllowed](https://core.telegram.org/bots/api#writeaccessallowed). */
data class WriteAccessAllowed(
    val fromRequest: Boolean? = null,
    val webAppName: String? = null,
    val fromAttachmentMenu: Boolean? = null,
)

/** Plain string used where the spec allows a [RichText] value to be a String. */
data class RichTextPlain(
    val text: String = "",
) : RichText

/** List used where the spec allows a [RichText] value to be an array. */
data class RichTextParts(
    val parts: List<RichText> = emptyList(),
) : RichText

