// Generated from Bot API 10.3 (August 24, 2026). Do not edit.
// Regenerate: ./gradlew :bot:generateBotApi
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
class AcceptedGiftTypes(
    val unlimitedGifts: Boolean = false,
    val limitedGifts: Boolean = false,
    val uniqueGifts: Boolean = false,
    val premiumSubscription: Boolean = false,
    val giftsFromChannels: Boolean = false,
)

/** [AffiliateInfo](https://core.telegram.org/bots/api#affiliateinfo). */
class AffiliateInfo(
    val affiliateUser: User? = null,
    val affiliateChat: Chat? = null,
    val commissionPerMille: Int = 0,
    val amount: Int = 0,
    val nanostarAmount: Int? = null,
)

/** [Animation](https://core.telegram.org/bots/api#animation). */
class Animation(
    val fileId: String = "",
    val fileUniqueId: String = "",
    val width: Int = 0,
    val height: Int = 0,
    val duration: Int = 0,
    val thumbnail: PhotoSize? = null,
    val fileName: String? = null,
    val mimeType: String? = null,
    val fileSize: Long? = null,
)

/** [Audio](https://core.telegram.org/bots/api#audio). */
class Audio(
    val fileId: String = "",
    val fileUniqueId: String = "",
    val duration: Int = 0,
    val performer: String? = null,
    val title: String? = null,
    val fileName: String? = null,
    val mimeType: String? = null,
    val fileSize: Long? = null,
    val thumbnail: PhotoSize? = null,
)

/** [BackgroundFillFreeformGradient](https://core.telegram.org/bots/api#backgroundfillfreeformgradient). */
class BackgroundFillFreeformGradient(
    val type: String = "",
    val colors: List<Int> = emptyList(),
) : BackgroundFill

/** [BackgroundFillGradient](https://core.telegram.org/bots/api#backgroundfillgradient). */
class BackgroundFillGradient(
    val type: String = "",
    val topColor: Int = 0,
    val bottomColor: Int = 0,
    val rotationAngle: Int = 0,
) : BackgroundFill

/** [BackgroundFillSolid](https://core.telegram.org/bots/api#backgroundfillsolid). */
class BackgroundFillSolid(
    val type: String = "",
    val color: Int = 0,
) : BackgroundFill

/** [BackgroundTypeChatTheme](https://core.telegram.org/bots/api#backgroundtypechattheme). */
class BackgroundTypeChatTheme(
    val type: String = "",
    val themeName: String = "",
) : BackgroundType

/** [BackgroundTypeFill](https://core.telegram.org/bots/api#backgroundtypefill). */
class BackgroundTypeFill(
    val type: String = "",
    val fill: BackgroundFill? = null,
    val darkThemeDimming: Int = 0,
) : BackgroundType

/** [BackgroundTypePattern](https://core.telegram.org/bots/api#backgroundtypepattern). */
class BackgroundTypePattern(
    val type: String = "",
    val document: Document? = null,
    val fill: BackgroundFill? = null,
    val intensity: Int = 0,
    val isInverted: Boolean? = null,
    val isMoving: Boolean? = null,
) : BackgroundType

/** [BackgroundTypeWallpaper](https://core.telegram.org/bots/api#backgroundtypewallpaper). */
class BackgroundTypeWallpaper(
    val type: String = "",
    val document: Document? = null,
    val darkThemeDimming: Int = 0,
    val isBlurred: Boolean? = null,
    val isMoving: Boolean? = null,
) : BackgroundType

/** [Birthdate](https://core.telegram.org/bots/api#birthdate). */
class Birthdate(
    val day: Int = 0,
    val month: Int = 0,
    val year: Int? = null,
)

/** [BotAccessSettings](https://core.telegram.org/bots/api#botaccesssettings). */
class BotAccessSettings(
    val isAccessRestricted: Boolean = false,
    val addedUsers: List<User>? = null,
)

/** [BotCommand](https://core.telegram.org/bots/api#botcommand). */
class BotCommand(
    val command: String = "",
    val description: String = "",
    val isEphemeral: Boolean? = null,
)

/** [BotCommandScopeAllChatAdministrators](https://core.telegram.org/bots/api#botcommandscopeallchatadministrators). */
class BotCommandScopeAllChatAdministrators(
    val type: String = "",
) : BotCommandScope

/** [BotCommandScopeAllGroupChats](https://core.telegram.org/bots/api#botcommandscopeallgroupchats). */
class BotCommandScopeAllGroupChats(
    val type: String = "",
) : BotCommandScope

/** [BotCommandScopeAllPrivateChats](https://core.telegram.org/bots/api#botcommandscopeallprivatechats). */
class BotCommandScopeAllPrivateChats(
    val type: String = "",
) : BotCommandScope

/** [BotCommandScopeChat](https://core.telegram.org/bots/api#botcommandscopechat). */
class BotCommandScopeChat(
    val type: String = "",
    val chatId: LongOrString? = null,
) : BotCommandScope

/** [BotCommandScopeChatAdministrators](https://core.telegram.org/bots/api#botcommandscopechatadministrators). */
class BotCommandScopeChatAdministrators(
    val type: String = "",
    val chatId: LongOrString? = null,
) : BotCommandScope

/** [BotCommandScopeChatMember](https://core.telegram.org/bots/api#botcommandscopechatmember). */
class BotCommandScopeChatMember(
    val type: String = "",
    val chatId: LongOrString? = null,
    val userId: Long = 0,
) : BotCommandScope

/** [BotCommandScopeDefault](https://core.telegram.org/bots/api#botcommandscopedefault). */
class BotCommandScopeDefault(
    val type: String = "",
) : BotCommandScope

/** [BotDescription](https://core.telegram.org/bots/api#botdescription). */
class BotDescription(
    val description: String = "",
)

/** [BotName](https://core.telegram.org/bots/api#botname). */
class BotName(
    val name: String = "",
)

/** [BotShortDescription](https://core.telegram.org/bots/api#botshortdescription). */
class BotShortDescription(
    val shortDescription: String = "",
)

/** [BotSubscriptionUpdated](https://core.telegram.org/bots/api#botsubscriptionupdated). */
class BotSubscriptionUpdated(
    val user: User? = null,
    val invoicePayload: String = "",
    val state: String = "",
)

/** [BusinessBotRights](https://core.telegram.org/bots/api#businessbotrights). */
class BusinessBotRights(
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
class BusinessConnection(
    val id: String = "",
    val user: User? = null,
    val userChatId: Long = 0,
    val date: Int = 0,
    val rights: BusinessBotRights? = null,
    val isEnabled: Boolean = false,
)

/** [BusinessIntro](https://core.telegram.org/bots/api#businessintro). */
class BusinessIntro(
    val title: String? = null,
    val message: String? = null,
    val sticker: Sticker? = null,
)

/** [BusinessLocation](https://core.telegram.org/bots/api#businesslocation). */
class BusinessLocation(
    val address: String = "",
    val location: Location? = null,
)

/** [BusinessMessagesDeleted](https://core.telegram.org/bots/api#businessmessagesdeleted). */
class BusinessMessagesDeleted(
    val businessConnectionId: String = "",
    val chat: Chat? = null,
    val messageIds: List<Int> = emptyList(),
)

/** [BusinessOpeningHours](https://core.telegram.org/bots/api#businessopeninghours). */
class BusinessOpeningHours(
    val timeZoneName: String = "",
    val openingHours: List<BusinessOpeningHoursInterval> = emptyList(),
)

/** [BusinessOpeningHoursInterval](https://core.telegram.org/bots/api#businessopeninghoursinterval). */
class BusinessOpeningHoursInterval(
    val openingMinute: Int = 0,
    val closingMinute: Int = 0,
)

/** [CallbackGame](https://core.telegram.org/bots/api#callbackgame). */
data object CallbackGame

/** [CallbackQuery](https://core.telegram.org/bots/api#callbackquery). */
class CallbackQuery(
    val id: String = "",
    val from: User? = null,
    val message: MaybeInaccessibleMessage? = null,
    val inlineMessageId: String? = null,
    val chatInstance: String = "",
    val data: String? = null,
    val gameShortName: String? = null,
)

/** [Chat](https://core.telegram.org/bots/api#chat). */
class Chat(
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
class ChatAdministratorRights(
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
class ChatBackground(
    val type: BackgroundType? = null,
)

/** [ChatBoost](https://core.telegram.org/bots/api#chatboost). */
class ChatBoost(
    val boostId: String = "",
    val addDate: Int = 0,
    val expirationDate: Int = 0,
    val source: ChatBoostSource? = null,
)

/** [ChatBoostAdded](https://core.telegram.org/bots/api#chatboostadded). */
class ChatBoostAdded(
    val boostCount: Int = 0,
)

/** [ChatBoostRemoved](https://core.telegram.org/bots/api#chatboostremoved). */
class ChatBoostRemoved(
    val chat: Chat? = null,
    val boostId: String = "",
    val removeDate: Int = 0,
    val source: ChatBoostSource? = null,
)

/** [ChatBoostSourceGiftCode](https://core.telegram.org/bots/api#chatboostsourcegiftcode). */
class ChatBoostSourceGiftCode(
    val source: String = "",
    val user: User? = null,
) : ChatBoostSource

/** [ChatBoostSourceGiveaway](https://core.telegram.org/bots/api#chatboostsourcegiveaway). */
class ChatBoostSourceGiveaway(
    val source: String = "",
    val giveawayMessageId: Int = 0,
    val user: User? = null,
    val prizeStarCount: Int? = null,
    val isUnclaimed: Boolean? = null,
) : ChatBoostSource

/** [ChatBoostSourcePremium](https://core.telegram.org/bots/api#chatboostsourcepremium). */
class ChatBoostSourcePremium(
    val source: String = "",
    val user: User? = null,
) : ChatBoostSource

/** [ChatBoostUpdated](https://core.telegram.org/bots/api#chatboostupdated). */
class ChatBoostUpdated(
    val chat: Chat? = null,
    val boost: ChatBoost? = null,
)

/** [ChatFullInfo](https://core.telegram.org/bots/api#chatfullinfo). */
class ChatFullInfo(
    val id: Long = 0,
    val type: String = "",
    val title: String? = null,
    val username: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val isForum: Boolean? = null,
    val isDirectMessages: Boolean? = null,
    val accentColorId: Int = 0,
    val maxReactionCount: Int = 0,
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
    val profileAccentColorId: Int? = null,
    val profileBackgroundCustomEmojiId: String? = null,
    val emojiStatusCustomEmojiId: String? = null,
    val emojiStatusExpirationDate: Int? = null,
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
    val slowModeDelay: Int? = null,
    val unrestrictBoostCount: Int? = null,
    val messageAutoDeleteTime: Int? = null,
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
    val paidMessageStarCount: Int? = null,
    val guardBot: User? = null,
    val community: Community? = null,
)

/** [ChatInviteLink](https://core.telegram.org/bots/api#chatinvitelink). */
class ChatInviteLink(
    val inviteLink: String = "",
    val creator: User? = null,
    val createsJoinRequest: Boolean = false,
    val isPrimary: Boolean = false,
    val isRevoked: Boolean = false,
    val name: String? = null,
    val expireDate: Int? = null,
    val memberLimit: Int? = null,
    val pendingJoinRequestCount: Int? = null,
    val subscriptionPeriod: Int? = null,
    val subscriptionPrice: Int? = null,
)

/** [ChatJoinRequest](https://core.telegram.org/bots/api#chatjoinrequest). */
class ChatJoinRequest(
    val chat: Chat? = null,
    val from: User? = null,
    val userChatId: Long = 0,
    val date: Int = 0,
    val bio: String? = null,
    val inviteLink: ChatInviteLink? = null,
    val queryId: String? = null,
)

/** [ChatLocation](https://core.telegram.org/bots/api#chatlocation). */
class ChatLocation(
    val location: Location? = null,
    val address: String = "",
)

/** [ChatMemberAdministrator](https://core.telegram.org/bots/api#chatmemberadministrator). */
class ChatMemberAdministrator(
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
class ChatMemberBanned(
    val status: String = "",
    val user: User? = null,
    val untilDate: Int = 0,
) : ChatMember

/** [ChatMemberLeft](https://core.telegram.org/bots/api#chatmemberleft). */
class ChatMemberLeft(
    val status: String = "",
    val user: User? = null,
) : ChatMember

/** [ChatMemberMember](https://core.telegram.org/bots/api#chatmembermember). */
class ChatMemberMember(
    val status: String = "",
    val tag: String? = null,
    val user: User? = null,
    val untilDate: Int? = null,
) : ChatMember

/** [ChatMemberOwner](https://core.telegram.org/bots/api#chatmemberowner). */
class ChatMemberOwner(
    val status: String = "",
    val user: User? = null,
    val isAnonymous: Boolean = false,
    val customTitle: String? = null,
) : ChatMember

/** [ChatMemberRestricted](https://core.telegram.org/bots/api#chatmemberrestricted). */
class ChatMemberRestricted(
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
    val untilDate: Int = 0,
) : ChatMember

/** [ChatMemberUpdated](https://core.telegram.org/bots/api#chatmemberupdated). */
class ChatMemberUpdated(
    val chat: Chat? = null,
    val from: User? = null,
    val date: Int = 0,
    val oldChatMember: ChatMember? = null,
    val newChatMember: ChatMember? = null,
    val inviteLink: ChatInviteLink? = null,
    val viaJoinRequest: Boolean? = null,
    val viaChatFolderInviteLink: Boolean? = null,
)

/** [ChatOwnerChanged](https://core.telegram.org/bots/api#chatownerchanged). */
class ChatOwnerChanged(
    val newOwner: User? = null,
)

/** [ChatOwnerLeft](https://core.telegram.org/bots/api#chatownerleft). */
class ChatOwnerLeft(
    val newOwner: User? = null,
)

/** [ChatPermissions](https://core.telegram.org/bots/api#chatpermissions). */
class ChatPermissions(
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
class ChatPhoto(
    val smallFileId: String = "",
    val smallFileUniqueId: String = "",
    val bigFileId: String = "",
    val bigFileUniqueId: String = "",
)

/** [ChatShared](https://core.telegram.org/bots/api#chatshared). */
class ChatShared(
    val requestId: Int = 0,
    val chatId: Long = 0,
    val title: String? = null,
    val username: String? = null,
    val photo: List<PhotoSize>? = null,
)

/** [Checklist](https://core.telegram.org/bots/api#checklist). */
class Checklist(
    val title: String = "",
    val titleEntities: List<MessageEntity>? = null,
    val tasks: List<ChecklistTask> = emptyList(),
    val othersCanAddTasks: Boolean? = null,
    val othersCanMarkTasksAsDone: Boolean? = null,
)

/** [ChecklistTask](https://core.telegram.org/bots/api#checklisttask). */
class ChecklistTask(
    val id: Int = 0,
    val text: String = "",
    val textEntities: List<MessageEntity>? = null,
    val completedByUser: User? = null,
    val completedByChat: Chat? = null,
    val completionDate: Int? = null,
)

/** [ChecklistTasksAdded](https://core.telegram.org/bots/api#checklisttasksadded). */
class ChecklistTasksAdded(
    val checklistMessage: Message? = null,
    val tasks: List<ChecklistTask> = emptyList(),
)

/** [ChecklistTasksDone](https://core.telegram.org/bots/api#checklisttasksdone). */
class ChecklistTasksDone(
    val checklistMessage: Message? = null,
    val markedAsDoneTaskIds: List<Int>? = null,
    val markedAsNotDoneTaskIds: List<Int>? = null,
)

/** [ChosenInlineResult](https://core.telegram.org/bots/api#choseninlineresult). */
class ChosenInlineResult(
    val resultId: String = "",
    val from: User? = null,
    val location: Location? = null,
    val inlineMessageId: String? = null,
    val query: String = "",
)

/** [Community](https://core.telegram.org/bots/api#community). */
class Community(
    val id: Long = 0,
    val name: String = "",
)

/** [CommunityChatAdded](https://core.telegram.org/bots/api#communitychatadded). */
class CommunityChatAdded(
    val community: Community? = null,
)

/** [CommunityChatJoined](https://core.telegram.org/bots/api#communitychatjoined). */
class CommunityChatJoined(
    val community: Community? = null,
)

/** [CommunityChatRemoved](https://core.telegram.org/bots/api#communitychatremoved). */
data object CommunityChatRemoved

/** [Contact](https://core.telegram.org/bots/api#contact). */
class Contact(
    val phoneNumber: String = "",
    val firstName: String = "",
    val lastName: String? = null,
    val userId: Long? = null,
    val vcard: String? = null,
)

/** [CopyTextButton](https://core.telegram.org/bots/api#copytextbutton). */
class CopyTextButton(
    val text: String = "",
)

/** [Dice](https://core.telegram.org/bots/api#dice). */
class Dice(
    val emoji: String = "",
    val value: Int = 0,
)

/** [DirectMessagePriceChanged](https://core.telegram.org/bots/api#directmessagepricechanged). */
class DirectMessagePriceChanged(
    val areDirectMessagesEnabled: Boolean = false,
    val directMessageStarCount: Int? = null,
)

/** [DirectMessagesTopic](https://core.telegram.org/bots/api#directmessagestopic). */
class DirectMessagesTopic(
    val topicId: Long = 0,
    val user: User? = null,
)

/** [DisabledButton](https://core.telegram.org/bots/api#disabledbutton). */
data object DisabledButton

/** [Document](https://core.telegram.org/bots/api#document). */
class Document(
    val fileId: String = "",
    val fileUniqueId: String = "",
    val thumbnail: PhotoSize? = null,
    val fileName: String? = null,
    val mimeType: String? = null,
    val fileSize: Long? = null,
)

/** [EncryptedCredentials](https://core.telegram.org/bots/api#encryptedcredentials). */
class EncryptedCredentials(
    val data: String = "",
    val hash: String = "",
    val secret: String = "",
)

/** [EncryptedPassportElement](https://core.telegram.org/bots/api#encryptedpassportelement). */
class EncryptedPassportElement(
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
class EphemeralMessageParameters(
    val receiverUserId: Long = 0,
    val callbackQueryId: String? = null,
    val replaceCallbackQueryMessage: Boolean? = null,
)

/** [ExternalReplyInfo](https://core.telegram.org/bots/api#externalreplyinfo). */
class ExternalReplyInfo(
    val origin: MessageOrigin? = null,
    val chat: Chat? = null,
    val messageId: Int? = null,
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
class File(
    val fileId: String = "",
    val fileUniqueId: String = "",
    val fileSize: Long? = null,
    val filePath: String? = null,
)

/** [ForceReply](https://core.telegram.org/bots/api#forcereply). */
class ForceReply(
    val forceReply: Boolean = false,
    val inputFieldPlaceholder: String? = null,
    val selective: Boolean? = null,
)

/** [ForumTopic](https://core.telegram.org/bots/api#forumtopic). */
class ForumTopic(
    val messageThreadId: Int = 0,
    val name: String = "",
    val iconColor: Int = 0,
    val iconCustomEmojiId: String? = null,
    val isNameImplicit: Boolean? = null,
)

/** [ForumTopicClosed](https://core.telegram.org/bots/api#forumtopicclosed). */
data object ForumTopicClosed

/** [ForumTopicCreated](https://core.telegram.org/bots/api#forumtopiccreated). */
class ForumTopicCreated(
    val name: String = "",
    val iconColor: Int = 0,
    val iconCustomEmojiId: String? = null,
    val isNameImplicit: Boolean? = null,
)

/** [ForumTopicEdited](https://core.telegram.org/bots/api#forumtopicedited). */
class ForumTopicEdited(
    val name: String? = null,
    val iconCustomEmojiId: String? = null,
)

/** [ForumTopicReopened](https://core.telegram.org/bots/api#forumtopicreopened). */
data object ForumTopicReopened

/** [Game](https://core.telegram.org/bots/api#game). */
class Game(
    val title: String = "",
    val description: String = "",
    val photo: List<PhotoSize> = emptyList(),
    val text: String? = null,
    val textEntities: List<MessageEntity>? = null,
    val animation: Animation? = null,
)

/** [GameHighScore](https://core.telegram.org/bots/api#gamehighscore). */
class GameHighScore(
    val position: Int = 0,
    val user: User? = null,
    val score: Int = 0,
)

/** [GeneralForumTopicHidden](https://core.telegram.org/bots/api#generalforumtopichidden). */
data object GeneralForumTopicHidden

/** [GeneralForumTopicUnhidden](https://core.telegram.org/bots/api#generalforumtopicunhidden). */
data object GeneralForumTopicUnhidden

/** [Gift](https://core.telegram.org/bots/api#gift). */
class Gift(
    val id: String = "",
    val sticker: Sticker? = null,
    val starCount: Int = 0,
    val upgradeStarCount: Int? = null,
    val isPremium: Boolean? = null,
    val hasColors: Boolean? = null,
    val totalCount: Int? = null,
    val remainingCount: Int? = null,
    val personalTotalCount: Int? = null,
    val personalRemainingCount: Int? = null,
    val background: GiftBackground? = null,
    val uniqueGiftVariantCount: Int? = null,
    val publisherChat: Chat? = null,
)

/** [GiftBackground](https://core.telegram.org/bots/api#giftbackground). */
class GiftBackground(
    val centerColor: Int = 0,
    val edgeColor: Int = 0,
    val textColor: Int = 0,
)

/** [GiftInfo](https://core.telegram.org/bots/api#giftinfo). */
class GiftInfo(
    val gift: Gift? = null,
    val ownedGiftId: String? = null,
    val convertStarCount: Int? = null,
    val prepaidUpgradeStarCount: Int? = null,
    val isUpgradeSeparate: Boolean? = null,
    val canBeUpgraded: Boolean? = null,
    val text: String? = null,
    val entities: List<MessageEntity>? = null,
    val isPrivate: Boolean? = null,
    val uniqueGiftNumber: Int? = null,
)

/** [Gifts](https://core.telegram.org/bots/api#gifts). */
class Gifts(
    val gifts: List<Gift> = emptyList(),
)

/** [Giveaway](https://core.telegram.org/bots/api#giveaway). */
class Giveaway(
    val chats: List<Chat> = emptyList(),
    val winnersSelectionDate: Int = 0,
    val winnerCount: Int = 0,
    val onlyNewMembers: Boolean? = null,
    val hasPublicWinners: Boolean? = null,
    val prizeDescription: String? = null,
    val countryCodes: List<String>? = null,
    val prizeStarCount: Int? = null,
    val premiumSubscriptionMonthCount: Int? = null,
)

/** [GiveawayCompleted](https://core.telegram.org/bots/api#giveawaycompleted). */
class GiveawayCompleted(
    val winnerCount: Int = 0,
    val unclaimedPrizeCount: Int? = null,
    val giveawayMessage: Message? = null,
    val isStarGiveaway: Boolean? = null,
)

/** [GiveawayCreated](https://core.telegram.org/bots/api#giveawaycreated). */
class GiveawayCreated(
    val prizeStarCount: Int? = null,
)

/** [GiveawayWinners](https://core.telegram.org/bots/api#giveawaywinners). */
class GiveawayWinners(
    val chat: Chat? = null,
    val giveawayMessageId: Int = 0,
    val winnersSelectionDate: Int = 0,
    val winnerCount: Int = 0,
    val winners: List<User> = emptyList(),
    val additionalChatCount: Int? = null,
    val prizeStarCount: Int? = null,
    val premiumSubscriptionMonthCount: Int? = null,
    val unclaimedPrizeCount: Int? = null,
    val onlyNewMembers: Boolean? = null,
    val wasRefunded: Boolean? = null,
    val prizeDescription: String? = null,
)

/** [InaccessibleMessage](https://core.telegram.org/bots/api#inaccessiblemessage). */
class InaccessibleMessage(
    val chat: Chat? = null,
    val messageId: Int = 0,
    val date: Int = 0,
) : MaybeInaccessibleMessage

/** [InlineKeyboardButton](https://core.telegram.org/bots/api#inlinekeyboardbutton). */
class InlineKeyboardButton(
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
class InlineKeyboardMarkup(
    val inlineKeyboard: List<List<InlineKeyboardButton>> = emptyList(),
    val forceReply: Boolean? = null,
)

/** [InlineQuery](https://core.telegram.org/bots/api#inlinequery). */
class InlineQuery(
    val id: String = "",
    val from: User? = null,
    val query: String = "",
    val offset: String = "",
    val chatType: String? = null,
    val location: Location? = null,
)

/** [InlineQueryResultArticle](https://core.telegram.org/bots/api#inlinequeryresultarticle). */
class InlineQueryResultArticle(
    val type: String = "",
    val id: String = "",
    val title: String = "",
    val inputMessageContent: InputMessageContent? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val url: String? = null,
    val description: String? = null,
    val thumbnailUrl: String? = null,
    val thumbnailWidth: Int? = null,
    val thumbnailHeight: Int? = null,
) : InlineQueryResult

/** [InlineQueryResultAudio](https://core.telegram.org/bots/api#inlinequeryresultaudio). */
class InlineQueryResultAudio(
    val type: String = "",
    val id: String = "",
    val audioUrl: String = "",
    val title: String = "",
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val performer: String? = null,
    val audioDuration: Int? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
) : InlineQueryResult

/** [InlineQueryResultCachedAudio](https://core.telegram.org/bots/api#inlinequeryresultcachedaudio). */
class InlineQueryResultCachedAudio(
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
class InlineQueryResultCachedDocument(
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
class InlineQueryResultCachedGif(
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
class InlineQueryResultCachedMpeg4Gif(
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
class InlineQueryResultCachedPhoto(
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
class InlineQueryResultCachedSticker(
    val type: String = "",
    val id: String = "",
    val stickerFileId: String = "",
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
) : InlineQueryResult

/** [InlineQueryResultCachedVideo](https://core.telegram.org/bots/api#inlinequeryresultcachedvideo). */
class InlineQueryResultCachedVideo(
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
class InlineQueryResultCachedVoice(
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
class InlineQueryResultContact(
    val type: String = "",
    val id: String = "",
    val phoneNumber: String = "",
    val firstName: String = "",
    val lastName: String? = null,
    val vcard: String? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
    val thumbnailUrl: String? = null,
    val thumbnailWidth: Int? = null,
    val thumbnailHeight: Int? = null,
) : InlineQueryResult

/** [InlineQueryResultDocument](https://core.telegram.org/bots/api#inlinequeryresultdocument). */
class InlineQueryResultDocument(
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
    val thumbnailWidth: Int? = null,
    val thumbnailHeight: Int? = null,
) : InlineQueryResult

/** [InlineQueryResultGame](https://core.telegram.org/bots/api#inlinequeryresultgame). */
class InlineQueryResultGame(
    val type: String = "",
    val id: String = "",
    val gameShortName: String = "",
    val replyMarkup: InlineKeyboardMarkup? = null,
) : InlineQueryResult

/** [InlineQueryResultGif](https://core.telegram.org/bots/api#inlinequeryresultgif). */
class InlineQueryResultGif(
    val type: String = "",
    val id: String = "",
    val gifUrl: String = "",
    val gifWidth: Int? = null,
    val gifHeight: Int? = null,
    val gifDuration: Int? = null,
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
class InlineQueryResultLocation(
    val type: String = "",
    val id: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val title: String = "",
    val horizontalAccuracy: Double? = null,
    val livePeriod: Int? = null,
    val heading: Int? = null,
    val proximityAlertRadius: Int? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
    val thumbnailUrl: String? = null,
    val thumbnailWidth: Int? = null,
    val thumbnailHeight: Int? = null,
) : InlineQueryResult

/** [InlineQueryResultMpeg4Gif](https://core.telegram.org/bots/api#inlinequeryresultmpeg4gif). */
class InlineQueryResultMpeg4Gif(
    val type: String = "",
    val id: String = "",
    val mpeg4Url: String = "",
    val mpeg4Width: Int? = null,
    val mpeg4Height: Int? = null,
    val mpeg4Duration: Int? = null,
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
class InlineQueryResultPhoto(
    val type: String = "",
    val id: String = "",
    val photoUrl: String = "",
    val thumbnailUrl: String = "",
    val photoWidth: Int? = null,
    val photoHeight: Int? = null,
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
class InlineQueryResultVenue(
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
    val thumbnailWidth: Int? = null,
    val thumbnailHeight: Int? = null,
) : InlineQueryResult

/** [InlineQueryResultVideo](https://core.telegram.org/bots/api#inlinequeryresultvideo). */
class InlineQueryResultVideo(
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
    val videoWidth: Int? = null,
    val videoHeight: Int? = null,
    val videoDuration: Int? = null,
    val description: String? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
) : InlineQueryResult

/** [InlineQueryResultVoice](https://core.telegram.org/bots/api#inlinequeryresultvoice). */
class InlineQueryResultVoice(
    val type: String = "",
    val id: String = "",
    val voiceUrl: String = "",
    val title: String = "",
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val voiceDuration: Int? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val inputMessageContent: InputMessageContent? = null,
) : InlineQueryResult

/** [InlineQueryResultsButton](https://core.telegram.org/bots/api#inlinequeryresultsbutton). */
class InlineQueryResultsButton(
    val text: String = "",
    val webApp: WebAppInfo? = null,
    val startParameter: String? = null,
)

/** [InputChecklist](https://core.telegram.org/bots/api#inputchecklist). */
class InputChecklist(
    val title: String = "",
    val parseMode: String? = null,
    val titleEntities: List<MessageEntity>? = null,
    val tasks: List<InputChecklistTask> = emptyList(),
    val othersCanAddTasks: Boolean? = null,
    val othersCanMarkTasksAsDone: Boolean? = null,
)

/** [InputChecklistTask](https://core.telegram.org/bots/api#inputchecklisttask). */
class InputChecklistTask(
    val id: Int = 0,
    val text: String = "",
    val parseMode: String? = null,
    val textEntities: List<MessageEntity>? = null,
)

/** [InputContactMessageContent](https://core.telegram.org/bots/api#inputcontactmessagecontent). */
class InputContactMessageContent(
    val phoneNumber: String = "",
    val firstName: String = "",
    val lastName: String? = null,
    val vcard: String? = null,
) : InputMessageContent

/** [InputFile](https://core.telegram.org/bots/api#inputfile). Path, file_id, or attach:// name. */
class InputFile(
    val value: String = "",
)

/** [InputInvoiceMessageContent](https://core.telegram.org/bots/api#inputinvoicemessagecontent). */
class InputInvoiceMessageContent(
    val title: String = "",
    val description: String = "",
    val payload: String = "",
    val providerToken: String? = null,
    val currency: String = "",
    val prices: List<LabeledPrice> = emptyList(),
    val maxTipAmount: Int? = null,
    val suggestedTipAmounts: List<Int>? = null,
    val providerData: String? = null,
    val photoUrl: String? = null,
    val photoSize: Int? = null,
    val photoWidth: Int? = null,
    val photoHeight: Int? = null,
    val needName: Boolean? = null,
    val needPhoneNumber: Boolean? = null,
    val needEmail: Boolean? = null,
    val needShippingAddress: Boolean? = null,
    val sendPhoneNumberToProvider: Boolean? = null,
    val sendEmailToProvider: Boolean? = null,
    val isFlexible: Boolean? = null,
) : InputMessageContent

/** [InputLocationMessageContent](https://core.telegram.org/bots/api#inputlocationmessagecontent). */
class InputLocationMessageContent(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val horizontalAccuracy: Double? = null,
    val livePeriod: Int? = null,
    val heading: Int? = null,
    val proximityAlertRadius: Int? = null,
) : InputMessageContent

/** [InputMediaAnimation](https://core.telegram.org/bots/api#inputmediaanimation). */
class InputMediaAnimation(
    val type: String = "",
    val media: String = "",
    val thumbnail: String? = null,
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val showCaptionAboveMedia: Boolean? = null,
    val width: Int? = null,
    val height: Int? = null,
    val duration: Int? = null,
    val hasSpoiler: Boolean? = null,
) : InputPollMedia, InputPollOptionMedia, InputMedia, MediaOfInputRichMessageMedia

/** [InputMediaAudio](https://core.telegram.org/bots/api#inputmediaaudio). */
class InputMediaAudio(
    val type: String = "",
    val media: String = "",
    val thumbnail: String? = null,
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val duration: Int? = null,
    val performer: String? = null,
    val title: String? = null,
) : InputPollMedia, InputMedia, MediaOfInputRichMessageMedia

/** [InputMediaDocument](https://core.telegram.org/bots/api#inputmediadocument). */
class InputMediaDocument(
    val type: String = "",
    val media: String = "",
    val thumbnail: String? = null,
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val disableContentTypeDetection: Boolean? = null,
) : InputPollMedia, InputMedia, MediaOfInputRichMessageMedia

/** [InputMediaLink](https://core.telegram.org/bots/api#inputmedialink). */
class InputMediaLink(
    val type: String = "",
    val url: String = "",
) : InputPollOptionMedia

/** [InputMediaLivePhoto](https://core.telegram.org/bots/api#inputmedialivephoto). */
class InputMediaLivePhoto(
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
class InputMediaLocation(
    val type: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val horizontalAccuracy: Double? = null,
) : InputPollMedia, InputPollOptionMedia

/** [InputMediaPhoto](https://core.telegram.org/bots/api#inputmediaphoto). */
class InputMediaPhoto(
    val type: String = "",
    val media: String = "",
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val showCaptionAboveMedia: Boolean? = null,
    val hasSpoiler: Boolean? = null,
) : InputPollMedia, InputPollOptionMedia, InputMedia, MediaOfInputRichMessageMedia

/** [InputMediaSticker](https://core.telegram.org/bots/api#inputmediasticker). */
class InputMediaSticker(
    val type: String = "",
    val media: String = "",
    val emoji: String? = null,
) : InputPollOptionMedia

/** [InputMediaVenue](https://core.telegram.org/bots/api#inputmediavenue). */
class InputMediaVenue(
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
class InputMediaVideo(
    val type: String = "",
    val media: String = "",
    val thumbnail: String? = null,
    val cover: String? = null,
    val startTimestamp: Int? = null,
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val showCaptionAboveMedia: Boolean? = null,
    val width: Int? = null,
    val height: Int? = null,
    val duration: Int? = null,
    val supportsStreaming: Boolean? = null,
    val hasSpoiler: Boolean? = null,
) : InputPollMedia, InputPollOptionMedia, InputMedia, MediaOfInputRichMessageMedia

/** [InputMediaVoiceNote](https://core.telegram.org/bots/api#inputmediavoicenote). */
class InputMediaVoiceNote(
    val type: String = "",
    val media: String = "",
    val caption: String? = null,
    val parseMode: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val duration: Int? = null,
) : MediaOfInputRichMessageMedia

/** [InputPaidMediaLivePhoto](https://core.telegram.org/bots/api#inputpaidmedialivephoto). */
class InputPaidMediaLivePhoto(
    val type: String = "",
    val media: String = "",
    val photo: String = "",
) : InputPaidMedia

/** [InputPaidMediaPhoto](https://core.telegram.org/bots/api#inputpaidmediaphoto). */
class InputPaidMediaPhoto(
    val type: String = "",
    val media: String = "",
) : InputPaidMedia

/** [InputPaidMediaVideo](https://core.telegram.org/bots/api#inputpaidmediavideo). */
class InputPaidMediaVideo(
    val type: String = "",
    val media: String = "",
    val thumbnail: String? = null,
    val cover: String? = null,
    val startTimestamp: Int? = null,
    val width: Int? = null,
    val height: Int? = null,
    val duration: Int? = null,
    val supportsStreaming: Boolean? = null,
) : InputPaidMedia

/** [InputPollOption](https://core.telegram.org/bots/api#inputpolloption). */
class InputPollOption(
    val text: String = "",
    val textParseMode: String? = null,
    val textEntities: List<MessageEntity>? = null,
    val media: InputPollOptionMedia? = null,
)

/** [InputProfilePhotoAnimated](https://core.telegram.org/bots/api#inputprofilephotoanimated). */
class InputProfilePhotoAnimated(
    val type: String = "",
    val animation: String = "",
    val mainFrameTimestamp: Double? = null,
) : InputProfilePhoto

/** [InputProfilePhotoStatic](https://core.telegram.org/bots/api#inputprofilephotostatic). */
class InputProfilePhotoStatic(
    val type: String = "",
    val photo: String = "",
) : InputProfilePhoto

/** [InputRichBlockAnchor](https://core.telegram.org/bots/api#inputrichblockanchor). */
class InputRichBlockAnchor(
    val type: String = "",
    val name: String = "",
) : InputRichBlock

/** [InputRichBlockAnimation](https://core.telegram.org/bots/api#inputrichblockanimation). */
class InputRichBlockAnimation(
    val type: String = "",
    val animation: InputMediaAnimation? = null,
    val caption: RichBlockCaption? = null,
) : InputRichBlock

/** [InputRichBlockAudio](https://core.telegram.org/bots/api#inputrichblockaudio). */
class InputRichBlockAudio(
    val type: String = "",
    val audio: InputMediaAudio? = null,
    val caption: RichBlockCaption? = null,
) : InputRichBlock

/** [InputRichBlockBlockQuotation](https://core.telegram.org/bots/api#inputrichblockblockquotation). */
class InputRichBlockBlockQuotation(
    val type: String = "",
    val blocks: List<InputRichBlock> = emptyList(),
    val credit: RichText? = null,
) : InputRichBlock

/** [InputRichBlockButtons](https://core.telegram.org/bots/api#inputrichblockbuttons). */
class InputRichBlockButtons(
    val type: String = "",
    val buttons: List<RichMessageButton> = emptyList(),
    val align: String? = null,
) : InputRichBlock

/** [InputRichBlockCollage](https://core.telegram.org/bots/api#inputrichblockcollage). */
class InputRichBlockCollage(
    val type: String = "",
    val blocks: List<InputRichBlock> = emptyList(),
    val caption: RichBlockCaption? = null,
) : InputRichBlock

/** [InputRichBlockDetails](https://core.telegram.org/bots/api#inputrichblockdetails). */
class InputRichBlockDetails(
    val type: String = "",
    val summary: RichText? = null,
    val blocks: List<InputRichBlock> = emptyList(),
    val isOpen: Boolean? = null,
) : InputRichBlock

/** [InputRichBlockDivider](https://core.telegram.org/bots/api#inputrichblockdivider). */
class InputRichBlockDivider(
    val type: String = "",
) : InputRichBlock

/** [InputRichBlockDocument](https://core.telegram.org/bots/api#inputrichblockdocument). */
class InputRichBlockDocument(
    val type: String = "",
    val document: InputMediaDocument? = null,
    val caption: RichBlockCaption? = null,
) : InputRichBlock

/** [InputRichBlockExpandableBlockQuotation](https://core.telegram.org/bots/api#inputrichblockexpandableblockquotation). */
class InputRichBlockExpandableBlockQuotation(
    val type: String = "",
    val text: RichText? = null,
    val credit: RichText? = null,
) : InputRichBlock

/** [InputRichBlockFooter](https://core.telegram.org/bots/api#inputrichblockfooter). */
class InputRichBlockFooter(
    val type: String = "",
    val text: RichText? = null,
) : InputRichBlock

/** [InputRichBlockList](https://core.telegram.org/bots/api#inputrichblocklist). */
class InputRichBlockList(
    val type: String = "",
    val items: List<InputRichBlockListItem> = emptyList(),
) : InputRichBlock

/** [InputRichBlockListItem](https://core.telegram.org/bots/api#inputrichblocklistitem). */
class InputRichBlockListItem(
    val blocks: List<InputRichBlock> = emptyList(),
    val hasCheckbox: Boolean? = null,
    val isChecked: Boolean? = null,
    val value: Int? = null,
    val type: String? = null,
)

/** [InputRichBlockMap](https://core.telegram.org/bots/api#inputrichblockmap). */
class InputRichBlockMap(
    val type: String = "",
    val location: Location? = null,
    val zoom: Int? = null,
    val width: Int? = null,
    val height: Int? = null,
    val caption: RichBlockCaption? = null,
) : InputRichBlock

/** [InputRichBlockMathematicalExpression](https://core.telegram.org/bots/api#inputrichblockmathematicalexpression). */
class InputRichBlockMathematicalExpression(
    val type: String = "",
    val expression: String = "",
) : InputRichBlock

/** [InputRichBlockParagraph](https://core.telegram.org/bots/api#inputrichblockparagraph). */
class InputRichBlockParagraph(
    val type: String = "",
    val text: RichText? = null,
) : InputRichBlock

/** [InputRichBlockPhoto](https://core.telegram.org/bots/api#inputrichblockphoto). */
class InputRichBlockPhoto(
    val type: String = "",
    val photo: InputMediaPhoto? = null,
    val caption: RichBlockCaption? = null,
) : InputRichBlock

/** [InputRichBlockPreformatted](https://core.telegram.org/bots/api#inputrichblockpreformatted). */
class InputRichBlockPreformatted(
    val type: String = "",
    val text: RichText? = null,
    val language: String? = null,
) : InputRichBlock

/** [InputRichBlockPullQuotation](https://core.telegram.org/bots/api#inputrichblockpullquotation). */
class InputRichBlockPullQuotation(
    val type: String = "",
    val text: RichText? = null,
    val credit: RichText? = null,
) : InputRichBlock

/** [InputRichBlockSectionHeading](https://core.telegram.org/bots/api#inputrichblocksectionheading). */
class InputRichBlockSectionHeading(
    val type: String = "",
    val text: RichText? = null,
    val size: Int = 0,
) : InputRichBlock

/** [InputRichBlockSlideshow](https://core.telegram.org/bots/api#inputrichblockslideshow). */
class InputRichBlockSlideshow(
    val type: String = "",
    val blocks: List<InputRichBlock> = emptyList(),
    val caption: RichBlockCaption? = null,
) : InputRichBlock

/** [InputRichBlockTable](https://core.telegram.org/bots/api#inputrichblocktable). */
class InputRichBlockTable(
    val type: String = "",
    val cells: List<List<RichBlockTableCell>> = emptyList(),
    val isBordered: Boolean? = null,
    val isStriped: Boolean? = null,
    val isCompact: Boolean? = null,
    val caption: RichText? = null,
) : InputRichBlock

/** [InputRichBlockThinking](https://core.telegram.org/bots/api#inputrichblockthinking). */
class InputRichBlockThinking(
    val type: String = "",
    val text: RichText? = null,
) : InputRichBlock

/** [InputRichBlockVideo](https://core.telegram.org/bots/api#inputrichblockvideo). */
class InputRichBlockVideo(
    val type: String = "",
    val video: InputMediaVideo? = null,
    val caption: RichBlockCaption? = null,
) : InputRichBlock

/** [InputRichBlockVoiceNote](https://core.telegram.org/bots/api#inputrichblockvoicenote). */
class InputRichBlockVoiceNote(
    val type: String = "",
    val voiceNote: InputMediaVoiceNote? = null,
    val caption: RichBlockCaption? = null,
) : InputRichBlock

/** [InputRichMessage](https://core.telegram.org/bots/api#inputrichmessage). */
class InputRichMessage(
    val blocks: List<InputRichBlock>? = null,
    val html: String? = null,
    val markdown: String? = null,
    val media: List<InputRichMessageMedia>? = null,
    val isRtl: Boolean? = null,
    val skipEntityDetection: Boolean? = null,
)

/** [InputRichMessageContent](https://core.telegram.org/bots/api#inputrichmessagecontent). */
class InputRichMessageContent(
    val richMessage: InputRichMessage? = null,
) : InputMessageContent

/** [InputRichMessageMedia](https://core.telegram.org/bots/api#inputrichmessagemedia). */
class InputRichMessageMedia(
    val id: String = "",
    val media: MediaOfInputRichMessageMedia? = null,
)

/** [InputSticker](https://core.telegram.org/bots/api#inputsticker). */
class InputSticker(
    val sticker: String = "",
    val format: String = "",
    val emojiList: List<String> = emptyList(),
    val maskPosition: MaskPosition? = null,
    val keywords: List<String>? = null,
)

/** [InputStoryContentPhoto](https://core.telegram.org/bots/api#inputstorycontentphoto). */
class InputStoryContentPhoto(
    val type: String = "",
    val photo: String = "",
) : InputStoryContent

/** [InputStoryContentVideo](https://core.telegram.org/bots/api#inputstorycontentvideo). */
class InputStoryContentVideo(
    val type: String = "",
    val video: String = "",
    val duration: Double? = null,
    val coverFrameTimestamp: Double? = null,
    val isAnimation: Boolean? = null,
) : InputStoryContent

/** [InputTextMessageContent](https://core.telegram.org/bots/api#inputtextmessagecontent). */
class InputTextMessageContent(
    val messageText: String = "",
    val parseMode: String? = null,
    val entities: List<MessageEntity>? = null,
    val linkPreviewOptions: LinkPreviewOptions? = null,
) : InputMessageContent

/** [InputVenueMessageContent](https://core.telegram.org/bots/api#inputvenuemessagecontent). */
class InputVenueMessageContent(
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
class Invoice(
    val title: String = "",
    val description: String = "",
    val startParameter: String = "",
    val currency: String = "",
    val totalAmount: Int = 0,
)

/** [KeyboardButton](https://core.telegram.org/bots/api#keyboardbutton). */
class KeyboardButton(
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
class KeyboardButtonPollType(
    val type: String? = null,
)

/** [KeyboardButtonRequestChat](https://core.telegram.org/bots/api#keyboardbuttonrequestchat). */
class KeyboardButtonRequestChat(
    val requestId: Int = 0,
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
class KeyboardButtonRequestManagedBot(
    val requestId: Int = 0,
    val suggestedName: String? = null,
    val suggestedUsername: String? = null,
)

/** [KeyboardButtonRequestUsers](https://core.telegram.org/bots/api#keyboardbuttonrequestusers). */
class KeyboardButtonRequestUsers(
    val requestId: Int = 0,
    val userIsBot: Boolean? = null,
    val userIsPremium: Boolean? = null,
    val maxQuantity: Int? = null,
    val requestName: Boolean? = null,
    val requestUsername: Boolean? = null,
    val requestPhoto: Boolean? = null,
)

/** [LabeledPrice](https://core.telegram.org/bots/api#labeledprice). */
class LabeledPrice(
    val label: String = "",
    val amount: Int = 0,
)

/** [Link](https://core.telegram.org/bots/api#link). */
class Link(
    val url: String = "",
)

/** [LinkPreviewOptions](https://core.telegram.org/bots/api#linkpreviewoptions). */
class LinkPreviewOptions(
    val isDisabled: Boolean? = null,
    val url: String? = null,
    val preferSmallMedia: Boolean? = null,
    val preferLargeMedia: Boolean? = null,
    val showAboveText: Boolean? = null,
)

/** [LivePhoto](https://core.telegram.org/bots/api#livephoto). */
class LivePhoto(
    val photo: List<PhotoSize>? = null,
    val fileId: String = "",
    val fileUniqueId: String = "",
    val width: Int = 0,
    val height: Int = 0,
    val duration: Int = 0,
    val mimeType: String? = null,
    val fileSize: Long? = null,
)

/** [Location](https://core.telegram.org/bots/api#location). */
class Location(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val horizontalAccuracy: Double? = null,
    val livePeriod: Int? = null,
    val heading: Int? = null,
    val proximityAlertRadius: Int? = null,
)

/** [LocationAddress](https://core.telegram.org/bots/api#locationaddress). */
class LocationAddress(
    val countryCode: String = "",
    val state: String? = null,
    val city: String? = null,
    val street: String? = null,
)

/** [LoginUrl](https://core.telegram.org/bots/api#loginurl). */
class LoginUrl(
    val url: String = "",
    val forwardText: String? = null,
    val botUsername: String? = null,
    val requestWriteAccess: Boolean? = null,
)

/** [ManagedBotCreated](https://core.telegram.org/bots/api#managedbotcreated). */
class ManagedBotCreated(
    val bot: User? = null,
)

/** [ManagedBotUpdated](https://core.telegram.org/bots/api#managedbotupdated). */
class ManagedBotUpdated(
    val user: User? = null,
    val bot: User? = null,
)

/** [MaskPosition](https://core.telegram.org/bots/api#maskposition). */
class MaskPosition(
    val point: String = "",
    val xShift: Double = 0.0,
    val yShift: Double = 0.0,
    val scale: Double = 0.0,
)

/** [MenuButtonCommands](https://core.telegram.org/bots/api#menubuttoncommands). */
class MenuButtonCommands(
    val type: String = "",
) : MenuButton

/** [MenuButtonDefault](https://core.telegram.org/bots/api#menubuttondefault). */
class MenuButtonDefault(
    val type: String = "",
) : MenuButton

/** [MenuButtonWebApp](https://core.telegram.org/bots/api#menubuttonwebapp). */
class MenuButtonWebApp(
    val type: String = "",
    val text: String = "",
    val webApp: WebAppInfo? = null,
) : MenuButton

/** [Message](https://core.telegram.org/bots/api#message). */
class Message(
    val messageId: Int = 0,
    val messageThreadId: Int? = null,
    val directMessagesTopic: DirectMessagesTopic? = null,
    val from: User? = null,
    val senderChat: Chat? = null,
    val senderBoostCount: Int? = null,
    val senderBusinessBot: User? = null,
    val senderTag: String? = null,
    val receiverUser: User? = null,
    val ephemeralMessageId: Int? = null,
    val date: Int = 0,
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
    val replyToChecklistTaskId: Int? = null,
    val replyToPollOptionId: String? = null,
    val viaBot: User? = null,
    val guestBotCallerUser: User? = null,
    val guestBotCallerChat: Chat? = null,
    val editDate: Int? = null,
    val hasProtectedContent: Boolean? = null,
    val isFromOffline: Boolean? = null,
    val isPaidPost: Boolean? = null,
    val mediaGroupId: String? = null,
    val authorSignature: String? = null,
    val paidStarCount: Int? = null,
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
class MessageAutoDeleteTimerChanged(
    val messageAutoDeleteTime: Int = 0,
)

/** [MessageEntity](https://core.telegram.org/bots/api#messageentity). */
class MessageEntity(
    val type: String = "",
    val offset: Int = 0,
    val length: Int = 0,
    val url: String? = null,
    val user: User? = null,
    val language: String? = null,
    val customEmojiId: String? = null,
    val unixTime: Int? = null,
    val dateTimeFormat: String? = null,
)

/** [MessageGenerationStopped](https://core.telegram.org/bots/api#messagegenerationstopped). */
class MessageGenerationStopped(
    val chat: Chat? = null,
    val messageThreadId: Int? = null,
    val draftId: Int = 0,
)

/** [MessageId](https://core.telegram.org/bots/api#messageid). */
class MessageId(
    val messageId: Int = 0,
)

/** [MessageOriginChannel](https://core.telegram.org/bots/api#messageoriginchannel). */
class MessageOriginChannel(
    val type: String = "",
    val date: Int = 0,
    val chat: Chat? = null,
    val messageId: Int = 0,
    val authorSignature: String? = null,
) : MessageOrigin

/** [MessageOriginChat](https://core.telegram.org/bots/api#messageoriginchat). */
class MessageOriginChat(
    val type: String = "",
    val date: Int = 0,
    val senderChat: Chat? = null,
    val authorSignature: String? = null,
) : MessageOrigin

/** [MessageOriginHiddenUser](https://core.telegram.org/bots/api#messageoriginhiddenuser). */
class MessageOriginHiddenUser(
    val type: String = "",
    val date: Int = 0,
    val senderUserName: String = "",
) : MessageOrigin

/** [MessageOriginUser](https://core.telegram.org/bots/api#messageoriginuser). */
class MessageOriginUser(
    val type: String = "",
    val date: Int = 0,
    val senderUser: User? = null,
) : MessageOrigin

/** [MessageReactionCountUpdated](https://core.telegram.org/bots/api#messagereactioncountupdated). */
class MessageReactionCountUpdated(
    val chat: Chat? = null,
    val messageId: Int = 0,
    val date: Int = 0,
    val reactions: List<ReactionCount> = emptyList(),
)

/** [MessageReactionUpdated](https://core.telegram.org/bots/api#messagereactionupdated). */
class MessageReactionUpdated(
    val chat: Chat? = null,
    val messageId: Int = 0,
    val user: User? = null,
    val actorChat: Chat? = null,
    val date: Int = 0,
    val oldReaction: List<ReactionType> = emptyList(),
    val newReaction: List<ReactionType> = emptyList(),
)

/** [OrderInfo](https://core.telegram.org/bots/api#orderinfo). */
class OrderInfo(
    val name: String? = null,
    val phoneNumber: String? = null,
    val email: String? = null,
    val shippingAddress: ShippingAddress? = null,
)

/** [OwnedGiftRegular](https://core.telegram.org/bots/api#ownedgiftregular). */
class OwnedGiftRegular(
    val type: String = "",
    val gift: Gift? = null,
    val ownedGiftId: String? = null,
    val senderUser: User? = null,
    val sendDate: Int = 0,
    val text: String? = null,
    val entities: List<MessageEntity>? = null,
    val isPrivate: Boolean? = null,
    val isSaved: Boolean? = null,
    val canBeUpgraded: Boolean? = null,
    val wasRefunded: Boolean? = null,
    val convertStarCount: Int? = null,
    val prepaidUpgradeStarCount: Int? = null,
    val isUpgradeSeparate: Boolean? = null,
    val uniqueGiftNumber: Int? = null,
) : OwnedGift

/** [OwnedGiftUnique](https://core.telegram.org/bots/api#ownedgiftunique). */
class OwnedGiftUnique(
    val type: String = "",
    val gift: UniqueGift? = null,
    val ownedGiftId: String? = null,
    val senderUser: User? = null,
    val sendDate: Int = 0,
    val isSaved: Boolean? = null,
    val canBeTransferred: Boolean? = null,
    val transferStarCount: Int? = null,
    val nextTransferDate: Int? = null,
) : OwnedGift

/** [OwnedGifts](https://core.telegram.org/bots/api#ownedgifts). */
class OwnedGifts(
    val totalCount: Int = 0,
    val gifts: List<OwnedGift> = emptyList(),
    val nextOffset: String? = null,
)

/** [PaidMediaInfo](https://core.telegram.org/bots/api#paidmediainfo). */
class PaidMediaInfo(
    val starCount: Int = 0,
    val paidMedia: List<PaidMedia> = emptyList(),
)

/** [PaidMediaLivePhoto](https://core.telegram.org/bots/api#paidmedialivephoto). */
class PaidMediaLivePhoto(
    val type: String = "",
    val livePhoto: LivePhoto? = null,
) : PaidMedia

/** [PaidMediaPhoto](https://core.telegram.org/bots/api#paidmediaphoto). */
class PaidMediaPhoto(
    val type: String = "",
    val photo: List<PhotoSize> = emptyList(),
) : PaidMedia

/** [PaidMediaPreview](https://core.telegram.org/bots/api#paidmediapreview). */
class PaidMediaPreview(
    val type: String = "",
    val width: Int? = null,
    val height: Int? = null,
    val duration: Int? = null,
) : PaidMedia

/** [PaidMediaPurchased](https://core.telegram.org/bots/api#paidmediapurchased). */
class PaidMediaPurchased(
    val from: User? = null,
    val paidMediaPayload: String = "",
)

/** [PaidMediaVideo](https://core.telegram.org/bots/api#paidmediavideo). */
class PaidMediaVideo(
    val type: String = "",
    val video: Video? = null,
) : PaidMedia

/** [PaidMessagePriceChanged](https://core.telegram.org/bots/api#paidmessagepricechanged). */
class PaidMessagePriceChanged(
    val paidMessageStarCount: Int = 0,
)

/** [PassportData](https://core.telegram.org/bots/api#passportdata). */
class PassportData(
    val data: List<EncryptedPassportElement> = emptyList(),
    val credentials: EncryptedCredentials? = null,
)

/** [PassportElementErrorDataField](https://core.telegram.org/bots/api#passportelementerrordatafield). */
class PassportElementErrorDataField(
    val source: String = "",
    val type: String = "",
    val fieldName: String = "",
    val dataHash: String = "",
    val message: String = "",
) : PassportElementError

/** [PassportElementErrorFile](https://core.telegram.org/bots/api#passportelementerrorfile). */
class PassportElementErrorFile(
    val source: String = "",
    val type: String = "",
    val fileHash: String = "",
    val message: String = "",
) : PassportElementError

/** [PassportElementErrorFiles](https://core.telegram.org/bots/api#passportelementerrorfiles). */
class PassportElementErrorFiles(
    val source: String = "",
    val type: String = "",
    val fileHashes: List<String> = emptyList(),
    val message: String = "",
) : PassportElementError

/** [PassportElementErrorFrontSide](https://core.telegram.org/bots/api#passportelementerrorfrontside). */
class PassportElementErrorFrontSide(
    val source: String = "",
    val type: String = "",
    val fileHash: String = "",
    val message: String = "",
) : PassportElementError

/** [PassportElementErrorReverseSide](https://core.telegram.org/bots/api#passportelementerrorreverseside). */
class PassportElementErrorReverseSide(
    val source: String = "",
    val type: String = "",
    val fileHash: String = "",
    val message: String = "",
) : PassportElementError

/** [PassportElementErrorSelfie](https://core.telegram.org/bots/api#passportelementerrorselfie). */
class PassportElementErrorSelfie(
    val source: String = "",
    val type: String = "",
    val fileHash: String = "",
    val message: String = "",
) : PassportElementError

/** [PassportElementErrorTranslationFile](https://core.telegram.org/bots/api#passportelementerrortranslationfile). */
class PassportElementErrorTranslationFile(
    val source: String = "",
    val type: String = "",
    val fileHash: String = "",
    val message: String = "",
) : PassportElementError

/** [PassportElementErrorTranslationFiles](https://core.telegram.org/bots/api#passportelementerrortranslationfiles). */
class PassportElementErrorTranslationFiles(
    val source: String = "",
    val type: String = "",
    val fileHashes: List<String> = emptyList(),
    val message: String = "",
) : PassportElementError

/** [PassportElementErrorUnspecified](https://core.telegram.org/bots/api#passportelementerrorunspecified). */
class PassportElementErrorUnspecified(
    val source: String = "",
    val type: String = "",
    val elementHash: String = "",
    val message: String = "",
) : PassportElementError

/** [PassportFile](https://core.telegram.org/bots/api#passportfile). */
class PassportFile(
    val fileId: String = "",
    val fileUniqueId: String = "",
    val fileSize: Int = 0,
    val fileDate: Int = 0,
)

/** [PhotoSize](https://core.telegram.org/bots/api#photosize). */
class PhotoSize(
    val fileId: String = "",
    val fileUniqueId: String = "",
    val width: Int = 0,
    val height: Int = 0,
    val fileSize: Int? = null,
)

/** [Poll](https://core.telegram.org/bots/api#poll). */
class Poll(
    val id: String = "",
    val question: String = "",
    val questionEntities: List<MessageEntity>? = null,
    val options: List<PollOption> = emptyList(),
    val totalVoterCount: Int = 0,
    val isClosed: Boolean = false,
    val isAnonymous: Boolean = false,
    val type: String = "",
    val allowsMultipleAnswers: Boolean = false,
    val allowsRevoting: Boolean = false,
    val membersOnly: Boolean = false,
    val countryCodes: List<String>? = null,
    val correctOptionIds: List<Int>? = null,
    val explanation: String? = null,
    val explanationEntities: List<MessageEntity>? = null,
    val explanationMedia: PollMedia? = null,
    val openPeriod: Int? = null,
    val closeDate: Int? = null,
    val description: String? = null,
    val descriptionEntities: List<MessageEntity>? = null,
    val media: PollMedia? = null,
)

/** [PollAnswer](https://core.telegram.org/bots/api#pollanswer). */
class PollAnswer(
    val pollId: String = "",
    val voterChat: Chat? = null,
    val user: User? = null,
    val optionIds: List<Int> = emptyList(),
    val optionPersistentIds: List<String> = emptyList(),
)

/** [PollMedia](https://core.telegram.org/bots/api#pollmedia). */
class PollMedia(
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
class PollOption(
    val persistentId: String = "",
    val text: String = "",
    val textEntities: List<MessageEntity>? = null,
    val media: PollMedia? = null,
    val voterCount: Int = 0,
    val addedByUser: User? = null,
    val addedByChat: Chat? = null,
    val additionDate: Int? = null,
)

/** [PollOptionAdded](https://core.telegram.org/bots/api#polloptionadded). */
class PollOptionAdded(
    val pollMessage: MaybeInaccessibleMessage? = null,
    val optionPersistentId: String = "",
    val optionText: String = "",
    val optionTextEntities: List<MessageEntity>? = null,
)

/** [PollOptionDeleted](https://core.telegram.org/bots/api#polloptiondeleted). */
class PollOptionDeleted(
    val pollMessage: MaybeInaccessibleMessage? = null,
    val optionPersistentId: String = "",
    val optionText: String = "",
    val optionTextEntities: List<MessageEntity>? = null,
)

/** [PreCheckoutQuery](https://core.telegram.org/bots/api#precheckoutquery). */
class PreCheckoutQuery(
    val id: String = "",
    val from: User? = null,
    val currency: String = "",
    val totalAmount: Int = 0,
    val invoicePayload: String = "",
    val shippingOptionId: String? = null,
    val orderInfo: OrderInfo? = null,
)

/** [PreparedInlineMessage](https://core.telegram.org/bots/api#preparedinlinemessage). */
class PreparedInlineMessage(
    val id: String = "",
    val expirationDate: Int = 0,
)

/** [PreparedKeyboardButton](https://core.telegram.org/bots/api#preparedkeyboardbutton). */
class PreparedKeyboardButton(
    val id: String = "",
)

/** [ProximityAlertTriggered](https://core.telegram.org/bots/api#proximityalerttriggered). */
class ProximityAlertTriggered(
    val traveler: User? = null,
    val watcher: User? = null,
    val distance: Int = 0,
)

/** [ReactionCount](https://core.telegram.org/bots/api#reactioncount). */
class ReactionCount(
    val type: ReactionType? = null,
    val totalCount: Int = 0,
)

/** [ReactionTypeCustomEmoji](https://core.telegram.org/bots/api#reactiontypecustomemoji). */
class ReactionTypeCustomEmoji(
    val type: String = "",
    val customEmojiId: String = "",
) : ReactionType

/** [ReactionTypeEmoji](https://core.telegram.org/bots/api#reactiontypeemoji). */
class ReactionTypeEmoji(
    val type: String = "",
    val emoji: String = "",
) : ReactionType

/** [ReactionTypePaid](https://core.telegram.org/bots/api#reactiontypepaid). */
class ReactionTypePaid(
    val type: String = "",
) : ReactionType

/** [RefundedPayment](https://core.telegram.org/bots/api#refundedpayment). */
class RefundedPayment(
    val currency: String = "",
    val totalAmount: Int = 0,
    val invoicePayload: String = "",
    val telegramPaymentChargeId: String = "",
    val providerPaymentChargeId: String? = null,
)

/** [ReplyKeyboardMarkup](https://core.telegram.org/bots/api#replykeyboardmarkup). */
class ReplyKeyboardMarkup(
    val keyboard: List<List<KeyboardButton>> = emptyList(),
    val isPersistent: Boolean? = null,
    val resizeKeyboard: Boolean? = null,
    val oneTimeKeyboard: Boolean? = null,
    val inputFieldPlaceholder: String? = null,
    val selective: Boolean? = null,
    val forceReply: Boolean? = null,
)

/** [ReplyKeyboardRemove](https://core.telegram.org/bots/api#replykeyboardremove). */
class ReplyKeyboardRemove(
    val removeKeyboard: Boolean = false,
    val selective: Boolean? = null,
)

/** [ReplyParameters](https://core.telegram.org/bots/api#replyparameters). */
class ReplyParameters(
    val messageId: Int? = null,
    val chatId: LongOrString? = null,
    val ephemeralMessageId: Int? = null,
    val allowSendingWithoutReply: Boolean? = null,
    val quote: String? = null,
    val quoteParseMode: String? = null,
    val quoteEntities: List<MessageEntity>? = null,
    val quotePosition: Int? = null,
    val checklistTaskId: Int? = null,
    val pollOptionId: String? = null,
)

/** [ResponseParameters](https://core.telegram.org/bots/api#responseparameters). */
class ResponseParameters(
    val migrateToChatId: Long? = null,
    val retryAfter: Int? = null,
)

/** [RevenueWithdrawalStateFailed](https://core.telegram.org/bots/api#revenuewithdrawalstatefailed). */
class RevenueWithdrawalStateFailed(
    val type: String = "",
) : RevenueWithdrawalState

/** [RevenueWithdrawalStatePending](https://core.telegram.org/bots/api#revenuewithdrawalstatepending). */
class RevenueWithdrawalStatePending(
    val type: String = "",
) : RevenueWithdrawalState

/** [RevenueWithdrawalStateSucceeded](https://core.telegram.org/bots/api#revenuewithdrawalstatesucceeded). */
class RevenueWithdrawalStateSucceeded(
    val type: String = "",
    val date: Int = 0,
    val url: String = "",
) : RevenueWithdrawalState

/** [RichBlockAnchor](https://core.telegram.org/bots/api#richblockanchor). */
class RichBlockAnchor(
    val type: String = "",
    val name: String = "",
) : RichBlock

/** [RichBlockAnimation](https://core.telegram.org/bots/api#richblockanimation). */
class RichBlockAnimation(
    val type: String = "",
    val animation: Animation? = null,
    val hasSpoiler: Boolean? = null,
    val caption: RichBlockCaption? = null,
) : RichBlock

/** [RichBlockAudio](https://core.telegram.org/bots/api#richblockaudio). */
class RichBlockAudio(
    val type: String = "",
    val audio: Audio? = null,
    val caption: RichBlockCaption? = null,
) : RichBlock

/** [RichBlockBlockQuotation](https://core.telegram.org/bots/api#richblockblockquotation). */
class RichBlockBlockQuotation(
    val type: String = "",
    val blocks: List<RichBlock> = emptyList(),
    val credit: RichText? = null,
) : RichBlock

/** [RichBlockButtons](https://core.telegram.org/bots/api#richblockbuttons). */
class RichBlockButtons(
    val type: String = "",
    val buttons: List<RichMessageButton> = emptyList(),
    val align: String? = null,
) : RichBlock

/** [RichBlockCaption](https://core.telegram.org/bots/api#richblockcaption). */
class RichBlockCaption(
    val text: RichText? = null,
    val credit: RichText? = null,
)

/** [RichBlockCollage](https://core.telegram.org/bots/api#richblockcollage). */
class RichBlockCollage(
    val type: String = "",
    val blocks: List<RichBlock> = emptyList(),
    val caption: RichBlockCaption? = null,
) : RichBlock

/** [RichBlockDetails](https://core.telegram.org/bots/api#richblockdetails). */
class RichBlockDetails(
    val type: String = "",
    val summary: RichText? = null,
    val blocks: List<RichBlock> = emptyList(),
    val isOpen: Boolean? = null,
) : RichBlock

/** [RichBlockDivider](https://core.telegram.org/bots/api#richblockdivider). */
class RichBlockDivider(
    val type: String = "",
) : RichBlock

/** [RichBlockDocument](https://core.telegram.org/bots/api#richblockdocument). */
class RichBlockDocument(
    val type: String = "",
    val document: Document? = null,
    val caption: RichBlockCaption? = null,
) : RichBlock

/** [RichBlockExpandableBlockQuotation](https://core.telegram.org/bots/api#richblockexpandableblockquotation). */
class RichBlockExpandableBlockQuotation(
    val type: String = "",
    val text: RichText? = null,
    val credit: RichText? = null,
) : RichBlock

/** [RichBlockFooter](https://core.telegram.org/bots/api#richblockfooter). */
class RichBlockFooter(
    val type: String = "",
    val text: RichText? = null,
) : RichBlock

/** [RichBlockList](https://core.telegram.org/bots/api#richblocklist). */
class RichBlockList(
    val type: String = "",
    val items: List<RichBlockListItem> = emptyList(),
) : RichBlock

/** [RichBlockListItem](https://core.telegram.org/bots/api#richblocklistitem). */
class RichBlockListItem(
    val label: String = "",
    val blocks: List<RichBlock> = emptyList(),
    val hasCheckbox: Boolean? = null,
    val isChecked: Boolean? = null,
    val value: Int? = null,
    val type: String? = null,
)

/** [RichBlockMap](https://core.telegram.org/bots/api#richblockmap). */
class RichBlockMap(
    val type: String = "",
    val location: Location? = null,
    val zoom: Int = 0,
    val width: Int = 0,
    val height: Int = 0,
    val caption: RichBlockCaption? = null,
) : RichBlock

/** [RichBlockMathematicalExpression](https://core.telegram.org/bots/api#richblockmathematicalexpression). */
class RichBlockMathematicalExpression(
    val type: String = "",
    val expression: String = "",
) : RichBlock

/** [RichBlockParagraph](https://core.telegram.org/bots/api#richblockparagraph). */
class RichBlockParagraph(
    val type: String = "",
    val text: RichText? = null,
) : RichBlock

/** [RichBlockPhoto](https://core.telegram.org/bots/api#richblockphoto). */
class RichBlockPhoto(
    val type: String = "",
    val photo: List<PhotoSize> = emptyList(),
    val hasSpoiler: Boolean? = null,
    val caption: RichBlockCaption? = null,
) : RichBlock

/** [RichBlockPreformatted](https://core.telegram.org/bots/api#richblockpreformatted). */
class RichBlockPreformatted(
    val type: String = "",
    val text: RichText? = null,
    val language: String? = null,
) : RichBlock

/** [RichBlockPullQuotation](https://core.telegram.org/bots/api#richblockpullquotation). */
class RichBlockPullQuotation(
    val type: String = "",
    val text: RichText? = null,
    val credit: RichText? = null,
) : RichBlock

/** [RichBlockSectionHeading](https://core.telegram.org/bots/api#richblocksectionheading). */
class RichBlockSectionHeading(
    val type: String = "",
    val text: RichText? = null,
    val size: Int = 0,
) : RichBlock

/** [RichBlockSlideshow](https://core.telegram.org/bots/api#richblockslideshow). */
class RichBlockSlideshow(
    val type: String = "",
    val blocks: List<RichBlock> = emptyList(),
    val caption: RichBlockCaption? = null,
) : RichBlock

/** [RichBlockTable](https://core.telegram.org/bots/api#richblocktable). */
class RichBlockTable(
    val type: String = "",
    val cells: List<List<RichBlockTableCell>> = emptyList(),
    val isBordered: Boolean? = null,
    val isStriped: Boolean? = null,
    val isCompact: Boolean? = null,
    val caption: RichText? = null,
) : RichBlock

/** [RichBlockTableCell](https://core.telegram.org/bots/api#richblocktablecell). */
class RichBlockTableCell(
    val text: RichText? = null,
    val isHeader: Boolean? = null,
    val colspan: Int? = null,
    val rowspan: Int? = null,
    val align: String = "",
    val valign: String = "",
)

/** [RichBlockThinking](https://core.telegram.org/bots/api#richblockthinking). */
class RichBlockThinking(
    val type: String = "",
    val text: RichText? = null,
) : RichBlock

/** [RichBlockVideo](https://core.telegram.org/bots/api#richblockvideo). */
class RichBlockVideo(
    val type: String = "",
    val video: Video? = null,
    val hasSpoiler: Boolean? = null,
    val caption: RichBlockCaption? = null,
) : RichBlock

/** [RichBlockVoiceNote](https://core.telegram.org/bots/api#richblockvoicenote). */
class RichBlockVoiceNote(
    val type: String = "",
    val voiceNote: Voice? = null,
    val caption: RichBlockCaption? = null,
) : RichBlock

/** [RichMessage](https://core.telegram.org/bots/api#richmessage). */
class RichMessage(
    val blocks: List<RichBlock> = emptyList(),
    val isRtl: Boolean? = null,
)

/** [RichMessageButton](https://core.telegram.org/bots/api#richmessagebutton). */
class RichMessageButton(
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
class RichTextAnchor(
    val type: String = "",
    val name: String = "",
) : RichText

/** [RichTextAnchorLink](https://core.telegram.org/bots/api#richtextanchorlink). */
class RichTextAnchorLink(
    val type: String = "",
    val text: RichText? = null,
    val anchorName: String = "",
) : RichText

/** [RichTextBankCardNumber](https://core.telegram.org/bots/api#richtextbankcardnumber). */
class RichTextBankCardNumber(
    val type: String = "",
    val text: RichText? = null,
    val bankCardNumber: String = "",
) : RichText

/** [RichTextBold](https://core.telegram.org/bots/api#richtextbold). */
class RichTextBold(
    val type: String = "",
    val text: RichText? = null,
) : RichText

/** [RichTextBotCommand](https://core.telegram.org/bots/api#richtextbotcommand). */
class RichTextBotCommand(
    val type: String = "",
    val text: RichText? = null,
    val botCommand: String = "",
) : RichText

/** [RichTextButton](https://core.telegram.org/bots/api#richtextbutton). */
class RichTextButton(
    val type: String = "",
    val button: RichMessageButton? = null,
) : RichText

/** [RichTextCashtag](https://core.telegram.org/bots/api#richtextcashtag). */
class RichTextCashtag(
    val type: String = "",
    val text: RichText? = null,
    val cashtag: String = "",
) : RichText

/** [RichTextCode](https://core.telegram.org/bots/api#richtextcode). */
class RichTextCode(
    val type: String = "",
    val text: RichText? = null,
) : RichText

/** [RichTextCustomEmoji](https://core.telegram.org/bots/api#richtextcustomemoji). */
class RichTextCustomEmoji(
    val type: String = "",
    val customEmojiId: String = "",
    val alternativeText: String = "",
) : RichText

/** [RichTextDateTime](https://core.telegram.org/bots/api#richtextdatetime). */
class RichTextDateTime(
    val type: String = "",
    val text: RichText? = null,
    val unixTime: Int = 0,
    val dateTimeFormat: String = "",
) : RichText

/** [RichTextEmailAddress](https://core.telegram.org/bots/api#richtextemailaddress). */
class RichTextEmailAddress(
    val type: String = "",
    val text: RichText? = null,
    val emailAddress: String = "",
) : RichText

/** [RichTextHashtag](https://core.telegram.org/bots/api#richtexthashtag). */
class RichTextHashtag(
    val type: String = "",
    val text: RichText? = null,
    val hashtag: String = "",
) : RichText

/** [RichTextItalic](https://core.telegram.org/bots/api#richtextitalic). */
class RichTextItalic(
    val type: String = "",
    val text: RichText? = null,
) : RichText

/** [RichTextMarked](https://core.telegram.org/bots/api#richtextmarked). */
class RichTextMarked(
    val type: String = "",
    val text: RichText? = null,
) : RichText

/** [RichTextMathematicalExpression](https://core.telegram.org/bots/api#richtextmathematicalexpression). */
class RichTextMathematicalExpression(
    val type: String = "",
    val expression: String = "",
) : RichText

/** [RichTextMention](https://core.telegram.org/bots/api#richtextmention). */
class RichTextMention(
    val type: String = "",
    val text: RichText? = null,
    val username: String = "",
) : RichText

/** [RichTextPhoneNumber](https://core.telegram.org/bots/api#richtextphonenumber). */
class RichTextPhoneNumber(
    val type: String = "",
    val text: RichText? = null,
    val phoneNumber: String = "",
) : RichText

/** [RichTextReference](https://core.telegram.org/bots/api#richtextreference). */
class RichTextReference(
    val type: String = "",
    val text: RichText? = null,
    val name: String = "",
) : RichText

/** [RichTextReferenceLink](https://core.telegram.org/bots/api#richtextreferencelink). */
class RichTextReferenceLink(
    val type: String = "",
    val text: RichText? = null,
    val referenceName: String = "",
) : RichText

/** [RichTextSpoiler](https://core.telegram.org/bots/api#richtextspoiler). */
class RichTextSpoiler(
    val type: String = "",
    val text: RichText? = null,
) : RichText

/** [RichTextStrikethrough](https://core.telegram.org/bots/api#richtextstrikethrough). */
class RichTextStrikethrough(
    val type: String = "",
    val text: RichText? = null,
) : RichText

/** [RichTextSubscript](https://core.telegram.org/bots/api#richtextsubscript). */
class RichTextSubscript(
    val type: String = "",
    val text: RichText? = null,
) : RichText

/** [RichTextSuperscript](https://core.telegram.org/bots/api#richtextsuperscript). */
class RichTextSuperscript(
    val type: String = "",
    val text: RichText? = null,
) : RichText

/** [RichTextTextMention](https://core.telegram.org/bots/api#richtexttextmention). */
class RichTextTextMention(
    val type: String = "",
    val text: RichText? = null,
    val user: User? = null,
) : RichText

/** [RichTextUnderline](https://core.telegram.org/bots/api#richtextunderline). */
class RichTextUnderline(
    val type: String = "",
    val text: RichText? = null,
) : RichText

/** [RichTextUrl](https://core.telegram.org/bots/api#richtexturl). */
class RichTextUrl(
    val type: String = "",
    val text: RichText? = null,
    val url: String = "",
) : RichText

/** [SentGuestMessage](https://core.telegram.org/bots/api#sentguestmessage). */
class SentGuestMessage(
    val inlineMessageId: String = "",
)

/** [SentWebAppMessage](https://core.telegram.org/bots/api#sentwebappmessage). */
class SentWebAppMessage(
    val inlineMessageId: String? = null,
)

/** [SharedUser](https://core.telegram.org/bots/api#shareduser). */
class SharedUser(
    val userId: Long = 0,
    val firstName: String? = null,
    val lastName: String? = null,
    val username: String? = null,
    val photo: List<PhotoSize>? = null,
)

/** [ShippingAddress](https://core.telegram.org/bots/api#shippingaddress). */
class ShippingAddress(
    val countryCode: String = "",
    val state: String = "",
    val city: String = "",
    val streetLine1: String = "",
    val streetLine2: String = "",
    val postCode: String = "",
)

/** [ShippingOption](https://core.telegram.org/bots/api#shippingoption). */
class ShippingOption(
    val id: String = "",
    val title: String = "",
    val prices: List<LabeledPrice> = emptyList(),
)

/** [ShippingQuery](https://core.telegram.org/bots/api#shippingquery). */
class ShippingQuery(
    val id: String = "",
    val from: User? = null,
    val invoicePayload: String = "",
    val shippingAddress: ShippingAddress? = null,
)

/** [StarAmount](https://core.telegram.org/bots/api#staramount). */
class StarAmount(
    val amount: Int = 0,
    val nanostarAmount: Int? = null,
)

/** [StarTransaction](https://core.telegram.org/bots/api#startransaction). */
class StarTransaction(
    val id: String = "",
    val amount: Int = 0,
    val nanostarAmount: Int? = null,
    val date: Int = 0,
    val source: TransactionPartner? = null,
    val receiver: TransactionPartner? = null,
)

/** [StarTransactions](https://core.telegram.org/bots/api#startransactions). */
class StarTransactions(
    val transactions: List<StarTransaction> = emptyList(),
)

/** [Sticker](https://core.telegram.org/bots/api#sticker). */
class Sticker(
    val fileId: String = "",
    val fileUniqueId: String = "",
    val type: String = "",
    val width: Int = 0,
    val height: Int = 0,
    val isAnimated: Boolean = false,
    val isVideo: Boolean = false,
    val thumbnail: PhotoSize? = null,
    val emoji: String? = null,
    val setName: String? = null,
    val premiumAnimation: File? = null,
    val maskPosition: MaskPosition? = null,
    val customEmojiId: String? = null,
    val needsRepainting: Boolean? = null,
    val fileSize: Int? = null,
)

/** [StickerSet](https://core.telegram.org/bots/api#stickerset). */
class StickerSet(
    val name: String = "",
    val title: String = "",
    val stickerType: String = "",
    val stickers: List<Sticker> = emptyList(),
    val thumbnail: PhotoSize? = null,
)

/** [Story](https://core.telegram.org/bots/api#story). */
class Story(
    val chat: Chat? = null,
    val id: Int = 0,
)

/** [StoryArea](https://core.telegram.org/bots/api#storyarea). */
class StoryArea(
    val position: StoryAreaPosition? = null,
    val type: StoryAreaType? = null,
)

/** [StoryAreaPosition](https://core.telegram.org/bots/api#storyareaposition). */
class StoryAreaPosition(
    val xPercentage: Double = 0.0,
    val yPercentage: Double = 0.0,
    val widthPercentage: Double = 0.0,
    val heightPercentage: Double = 0.0,
    val rotationAngle: Double = 0.0,
    val cornerRadiusPercentage: Double = 0.0,
)

/** [StoryAreaTypeLink](https://core.telegram.org/bots/api#storyareatypelink). */
class StoryAreaTypeLink(
    val type: String = "",
    val url: String = "",
) : StoryAreaType

/** [StoryAreaTypeLocation](https://core.telegram.org/bots/api#storyareatypelocation). */
class StoryAreaTypeLocation(
    val type: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: LocationAddress? = null,
) : StoryAreaType

/** [StoryAreaTypeSuggestedReaction](https://core.telegram.org/bots/api#storyareatypesuggestedreaction). */
class StoryAreaTypeSuggestedReaction(
    val type: String = "",
    val reactionType: ReactionType? = null,
    val isDark: Boolean? = null,
    val isFlipped: Boolean? = null,
) : StoryAreaType

/** [StoryAreaTypeUniqueGift](https://core.telegram.org/bots/api#storyareatypeuniquegift). */
class StoryAreaTypeUniqueGift(
    val type: String = "",
    val name: String = "",
) : StoryAreaType

/** [StoryAreaTypeWeather](https://core.telegram.org/bots/api#storyareatypeweather). */
class StoryAreaTypeWeather(
    val type: String = "",
    val temperature: Double = 0.0,
    val emoji: String = "",
    val backgroundColor: Int = 0,
) : StoryAreaType

/** [SuccessfulPayment](https://core.telegram.org/bots/api#successfulpayment). */
class SuccessfulPayment(
    val currency: String = "",
    val totalAmount: Int = 0,
    val invoicePayload: String = "",
    val subscriptionExpirationDate: Int? = null,
    val isRecurring: Boolean? = null,
    val isFirstRecurring: Boolean? = null,
    val shippingOptionId: String? = null,
    val orderInfo: OrderInfo? = null,
    val telegramPaymentChargeId: String = "",
    val providerPaymentChargeId: String = "",
)

/** [SuggestedPostApprovalFailed](https://core.telegram.org/bots/api#suggestedpostapprovalfailed). */
class SuggestedPostApprovalFailed(
    val suggestedPostMessage: Message? = null,
    val price: SuggestedPostPrice? = null,
)

/** [SuggestedPostApproved](https://core.telegram.org/bots/api#suggestedpostapproved). */
class SuggestedPostApproved(
    val suggestedPostMessage: Message? = null,
    val price: SuggestedPostPrice? = null,
    val sendDate: Int = 0,
)

/** [SuggestedPostDeclined](https://core.telegram.org/bots/api#suggestedpostdeclined). */
class SuggestedPostDeclined(
    val suggestedPostMessage: Message? = null,
    val comment: String? = null,
)

/** [SuggestedPostInfo](https://core.telegram.org/bots/api#suggestedpostinfo). */
class SuggestedPostInfo(
    val state: String = "",
    val price: SuggestedPostPrice? = null,
    val sendDate: Int? = null,
)

/** [SuggestedPostPaid](https://core.telegram.org/bots/api#suggestedpostpaid). */
class SuggestedPostPaid(
    val suggestedPostMessage: Message? = null,
    val currency: String = "",
    val amount: Int? = null,
    val starAmount: StarAmount? = null,
)

/** [SuggestedPostParameters](https://core.telegram.org/bots/api#suggestedpostparameters). */
class SuggestedPostParameters(
    val price: SuggestedPostPrice? = null,
    val sendDate: Int? = null,
)

/** [SuggestedPostPrice](https://core.telegram.org/bots/api#suggestedpostprice). */
class SuggestedPostPrice(
    val currency: String = "",
    val amount: Int = 0,
)

/** [SuggestedPostRefunded](https://core.telegram.org/bots/api#suggestedpostrefunded). */
class SuggestedPostRefunded(
    val suggestedPostMessage: Message? = null,
    val reason: String = "",
)

/** [SwitchInlineQueryChosenChat](https://core.telegram.org/bots/api#switchinlinequerychosenchat). */
class SwitchInlineQueryChosenChat(
    val query: String? = null,
    val allowUserChats: Boolean? = null,
    val allowBotChats: Boolean? = null,
    val allowGroupChats: Boolean? = null,
    val allowChannelChats: Boolean? = null,
)

/** [TextQuote](https://core.telegram.org/bots/api#textquote). */
class TextQuote(
    val text: String = "",
    val entities: List<MessageEntity>? = null,
    val position: Int = 0,
    val isManual: Boolean? = null,
)

/** [TransactionPartnerAffiliateProgram](https://core.telegram.org/bots/api#transactionpartneraffiliateprogram). */
class TransactionPartnerAffiliateProgram(
    val type: String = "",
    val sponsorUser: User? = null,
    val commissionPerMille: Int = 0,
) : TransactionPartner

/** [TransactionPartnerChat](https://core.telegram.org/bots/api#transactionpartnerchat). */
class TransactionPartnerChat(
    val type: String = "",
    val chat: Chat? = null,
    val gift: Gift? = null,
) : TransactionPartner

/** [TransactionPartnerFragment](https://core.telegram.org/bots/api#transactionpartnerfragment). */
class TransactionPartnerFragment(
    val type: String = "",
    val withdrawalState: RevenueWithdrawalState? = null,
) : TransactionPartner

/** [TransactionPartnerOther](https://core.telegram.org/bots/api#transactionpartnerother). */
class TransactionPartnerOther(
    val type: String = "",
) : TransactionPartner

/** [TransactionPartnerTelegramAds](https://core.telegram.org/bots/api#transactionpartnertelegramads). */
class TransactionPartnerTelegramAds(
    val type: String = "",
) : TransactionPartner

/** [TransactionPartnerTelegramApi](https://core.telegram.org/bots/api#transactionpartnertelegramapi). */
class TransactionPartnerTelegramApi(
    val type: String = "",
    val requestCount: Int = 0,
) : TransactionPartner

/** [TransactionPartnerUser](https://core.telegram.org/bots/api#transactionpartneruser). */
class TransactionPartnerUser(
    val type: String = "",
    val transactionType: String = "",
    val user: User? = null,
    val affiliate: AffiliateInfo? = null,
    val invoicePayload: String? = null,
    val subscriptionPeriod: Int? = null,
    val paidMedia: List<PaidMedia>? = null,
    val paidMediaPayload: String? = null,
    val gift: Gift? = null,
    val premiumSubscriptionDuration: Int? = null,
) : TransactionPartner

/** [UniqueGift](https://core.telegram.org/bots/api#uniquegift). */
class UniqueGift(
    val giftId: String = "",
    val baseName: String = "",
    val name: String = "",
    val number: Int = 0,
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
class UniqueGiftBackdrop(
    val name: String = "",
    val colors: UniqueGiftBackdropColors? = null,
    val rarityPerMille: Int = 0,
)

/** [UniqueGiftBackdropColors](https://core.telegram.org/bots/api#uniquegiftbackdropcolors). */
class UniqueGiftBackdropColors(
    val centerColor: Int = 0,
    val edgeColor: Int = 0,
    val symbolColor: Int = 0,
    val textColor: Int = 0,
)

/** [UniqueGiftColors](https://core.telegram.org/bots/api#uniquegiftcolors). */
class UniqueGiftColors(
    val modelCustomEmojiId: String = "",
    val symbolCustomEmojiId: String = "",
    val lightThemeMainColor: Int = 0,
    val lightThemeOtherColors: List<Int> = emptyList(),
    val darkThemeMainColor: Int = 0,
    val darkThemeOtherColors: List<Int> = emptyList(),
)

/** [UniqueGiftInfo](https://core.telegram.org/bots/api#uniquegiftinfo). */
class UniqueGiftInfo(
    val gift: UniqueGift? = null,
    val origin: String = "",
    val text: String? = null,
    val entities: List<MessageEntity>? = null,
    val isPrivate: Boolean? = null,
    val lastResaleCurrency: String? = null,
    val lastResaleAmount: Int? = null,
    val ownedGiftId: String? = null,
    val transferStarCount: Int? = null,
    val nextTransferDate: Int? = null,
)

/** [UniqueGiftModel](https://core.telegram.org/bots/api#uniquegiftmodel). */
class UniqueGiftModel(
    val name: String = "",
    val sticker: Sticker? = null,
    val rarityPerMille: Int = 0,
    val rarity: String? = null,
)

/** [UniqueGiftSymbol](https://core.telegram.org/bots/api#uniquegiftsymbol). */
class UniqueGiftSymbol(
    val name: String = "",
    val sticker: Sticker? = null,
    val rarityPerMille: Int = 0,
)

/** [Update](https://core.telegram.org/bots/api#update). */
class Update(
    val updateId: Int = 0,
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
class User(
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
class UserChatBoosts(
    val boosts: List<ChatBoost> = emptyList(),
)

/** [UserProfileAudios](https://core.telegram.org/bots/api#userprofileaudios). */
class UserProfileAudios(
    val totalCount: Int = 0,
    val audios: List<Audio> = emptyList(),
)

/** [UserProfilePhotos](https://core.telegram.org/bots/api#userprofilephotos). */
class UserProfilePhotos(
    val totalCount: Int = 0,
    val photos: List<List<PhotoSize>> = emptyList(),
)

/** [UserRating](https://core.telegram.org/bots/api#userrating). */
class UserRating(
    val level: Int = 0,
    val rating: Int = 0,
    val currentLevelRating: Int = 0,
    val nextLevelRating: Int? = null,
)

/** [UsersShared](https://core.telegram.org/bots/api#usersshared). */
class UsersShared(
    val requestId: Int = 0,
    val users: List<SharedUser> = emptyList(),
)

/** [Venue](https://core.telegram.org/bots/api#venue). */
class Venue(
    val location: Location? = null,
    val title: String = "",
    val address: String = "",
    val foursquareId: String? = null,
    val foursquareType: String? = null,
    val googlePlaceId: String? = null,
    val googlePlaceType: String? = null,
)

/** [Video](https://core.telegram.org/bots/api#video). */
class Video(
    val fileId: String = "",
    val fileUniqueId: String = "",
    val width: Int = 0,
    val height: Int = 0,
    val duration: Int = 0,
    val thumbnail: PhotoSize? = null,
    val cover: List<PhotoSize>? = null,
    val startTimestamp: Int? = null,
    val qualities: List<VideoQuality>? = null,
    val fileName: String? = null,
    val mimeType: String? = null,
    val fileSize: Long? = null,
)

/** [VideoChatEnded](https://core.telegram.org/bots/api#videochatended). */
class VideoChatEnded(
    val duration: Int = 0,
)

/** [VideoChatParticipantsInvited](https://core.telegram.org/bots/api#videochatparticipantsinvited). */
class VideoChatParticipantsInvited(
    val users: List<User> = emptyList(),
)

/** [VideoChatScheduled](https://core.telegram.org/bots/api#videochatscheduled). */
class VideoChatScheduled(
    val startDate: Int = 0,
)

/** [VideoChatStarted](https://core.telegram.org/bots/api#videochatstarted). */
data object VideoChatStarted

/** [VideoNote](https://core.telegram.org/bots/api#videonote). */
class VideoNote(
    val fileId: String = "",
    val fileUniqueId: String = "",
    val length: Int = 0,
    val duration: Int = 0,
    val thumbnail: PhotoSize? = null,
    val fileSize: Int? = null,
)

/** [VideoQuality](https://core.telegram.org/bots/api#videoquality). */
class VideoQuality(
    val fileId: String = "",
    val fileUniqueId: String = "",
    val width: Int = 0,
    val height: Int = 0,
    val codec: String = "",
    val fileSize: Long? = null,
)

/** [Voice](https://core.telegram.org/bots/api#voice). */
class Voice(
    val fileId: String = "",
    val fileUniqueId: String = "",
    val duration: Int = 0,
    val mimeType: String? = null,
    val fileSize: Long? = null,
)

/** [WebAppData](https://core.telegram.org/bots/api#webappdata). */
class WebAppData(
    val data: String = "",
    val buttonText: String = "",
)

/** [WebAppInfo](https://core.telegram.org/bots/api#webappinfo). */
class WebAppInfo(
    val url: String = "",
)

/** [WebhookInfo](https://core.telegram.org/bots/api#webhookinfo). */
class WebhookInfo(
    val url: String = "",
    val hasCustomCertificate: Boolean = false,
    val pendingUpdateCount: Int = 0,
    val ipAddress: String? = null,
    val lastErrorDate: Int? = null,
    val lastErrorMessage: String? = null,
    val lastSynchronizationErrorDate: Int? = null,
    val maxConnections: Int? = null,
    val allowedUpdates: List<String>? = null,
)

/** [WriteAccessAllowed](https://core.telegram.org/bots/api#writeaccessallowed). */
class WriteAccessAllowed(
    val fromRequest: Boolean? = null,
    val webAppName: String? = null,
    val fromAttachmentMenu: Boolean? = null,
)

/** Plain string used where the spec allows a [RichText] value to be a String. */
class RichTextPlain(
    val text: String = "",
) : RichText

/** List used where the spec allows a [RichText] value to be an array. */
class RichTextParts(
    val parts: List<RichText> = emptyList(),
) : RichText

