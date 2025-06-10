#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
 
#define DECK_SIZE 52
#define MAX_HAND 11
 
// ANSIカラー
#define RESET "\x1b[0m"
#define RED "\x1b[31m"
#define BLACK "\x1b[37m" // 白色として扱う
 
typedef struct
{
    int value; // 1-13: Ace to King
    char *suit;
} Card;
 
typedef struct
{
    Card cards[DECK_SIZE];
    int top;
} Deck;
 
typedef struct
{
    Card hand[MAX_HAND];
    int count;
    int chips;
} Player;
 
// カード表示パターン（1〜10用）
const int patterns[10][5][3] = {
    {{0, 0, 0}, {0, 0, 0}, {0, 1, 0}, {0, 0, 0}, {0, 0, 0}}, // A
    {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}, {0, 1, 0}, {0, 0, 0}}, // 2
    {{0, 0, 0}, {0, 1, 0}, {0, 1, 0}, {0, 1, 0}, {0, 0, 0}}, // 3
    {{0, 0, 0}, {1, 0, 1}, {0, 0, 0}, {1, 0, 1}, {0, 0, 0}}, // 4
    {{0, 0, 0}, {1, 0, 1}, {0, 1, 0}, {1, 0, 1}, {0, 0, 0}}, // 5
    {{0, 0, 0}, {1, 0, 1}, {1, 0, 1}, {1, 0, 1}, {0, 0, 0}}, // 6
    {{1, 0, 1}, {0, 1, 0}, {0, 1, 0}, {0, 1, 0}, {1, 0, 1}}, // 7
    {{1, 0, 1}, {0, 1, 0}, {1, 0, 1}, {0, 1, 0}, {1, 0, 1}}, // 8
    {{1, 0, 1}, {1, 0, 1}, {0, 1, 0}, {1, 0, 1}, {1, 0, 1}}, // 9
    {{1, 0, 1}, {1, 0, 1}, {1, 0, 1,}, {1, 0, 1},{1, 0, 1}}, // 10
};
 
char *suits[] = {"Hearts", "Diamonds", "Clubs", "Spades"};
char *faceNames[] = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
 
void initializeDeck(Deck *deck)
{
    int index = 0;
    for (int i = 0; i < 4; i++)
    {
        for (int j = 1; j <= 13; j++)
        {
            deck->cards[index].value = j;
            deck->cards[index].suit = suits[i];
            index++;
        }
    }
    deck->top = 0;
}
 
void shuffleDeck(Deck *deck)
{
    for (int i = 0; i < DECK_SIZE; i++)
    {
        int r = rand() % DECK_SIZE;
        Card temp = deck->cards[i];
        deck->cards[i] = deck->cards[r];
        deck->cards[r] = temp;
    }
}
 
Card drawCard(Deck *deck)
{
    return deck->cards[deck->top++];
}
 
void addCard(Player *player, Card card)
{
    if (player->count < MAX_HAND)
    {
        player->hand[player->count++] = card;
    }
}
 
int calculateScore(Player *player)
{
    int score = 0, aces = 0;
    for (int i = 0; i < player->count; i++)
    {
        int val = player->hand[i].value;
        if (val > 10)
            score += 10;
        else if (val == 1)
        {
            score += 11;
            aces++;
        }
        else
        {
            score += val;
        }
    }
    while (score > 21 && aces > 0)
    {
        score -= 10;
        aces--;
    }
    return score;
}
 
void printHandColor(Player *player)
{
    const int n = player->count;
    char lines[9][1024] = {{0}}; // 最大9行（0〜8）
 
    for (int row = 0; row < 9; row++)
    {
        lines[row][0] = '\0';
    }
 
    for (int i = 0; i < n; i++)
    {
        Card card = player->hand[i];
        char *face = faceNames[card.value - 1];
        char *symbol, *color;
 
        // スートで色と記号を決定
        if (strcmp(card.suit, "Hearts") == 0)
        {
            symbol = "♥";
            color = RED;
        }
        else if (strcmp(card.suit, "Diamonds") == 0)
        {
            symbol = "♦";
            color = RED;
        }
        else if (strcmp(card.suit, "Spades") == 0)
        {
            symbol = "♠";
            color = BLACK;
        }
        else
        {
            symbol = "♣";
            color = BLACK;
        }
 
        char buf[64];
 
        // 枠上部
        snprintf(buf, sizeof(buf), "┌─────────┐");
        strcat(lines[0], buf);
        if (i < n - 1)
            strcat(lines[0], " ");
 
        // 1行目（数字＋スート）
        snprintf(buf, sizeof(buf), "│ %s%-2s      %s│", color, face, RESET);
        strcat(lines[1], buf);
        if (i < n - 1)
            strcat(lines[1], " ");
 
        // 2〜6行（パターン or シンプル表示）
        if (card.value >= 1 && card.value <= 10)
        {
            int p = card.value - 1;
            for (int r = 0; r < 5; r++)
            {
                char markLine[16] = "";
                for (int c = 0; c < 3; c++)
                {
                    if (patterns[p][r][c])
                        strcat(markLine, symbol);
                    else
                        strcat(markLine, " ");
                }
                // マーク部分全体を色付きで囲う
                snprintf(buf, sizeof(buf), "│   %s%-3s%s   │", color, markLine, RESET);
                strcat(lines[r + 2], buf);
                if (i < n - 1)
                    strcat(lines[r + 2], " ");
            }
        }
        else
        {
            // J,Q,Kの場合は中央にマークを色付きで表示
            for (int r = 0; r < 5; r++)
            {
                if (r == 2)
                    snprintf(buf, sizeof(buf), "│    %s%s%s    │", color, symbol, RESET);
                else
                    snprintf(buf, sizeof(buf), "│         │");
                strcat(lines[r + 2], buf);
                if (i < n - 1)
                    strcat(lines[r + 2], " ");
            }
        }
 
        snprintf(buf, sizeof(buf), "│      %s%-2s%s │", color, face, RESET);
        strcat(lines[7], buf);
        if (i < n - 1)
            strcat(lines[7], " ");
 
        // 下枠
        snprintf(buf, sizeof(buf), "└─────────┘");
        strcat(lines[8], buf);
        if (i < n - 1)
            strcat(lines[8], " ");
    }
 
    //まとめて表示
    for (int i = 0; i < 9; i++)
    {
        printf("%s\n", lines[i]);
    }
}
 
 
void playerTurn(Player *player, Deck *deck)
{
    char choice;
    while (1)
    {
        printf("\nあなたの手札:\n");
        printHandColor(player);
        printf("\n現在のスコア: %d\n", calculateScore(player));
        if (calculateScore(player) > 21)
        {
            printf("バーストしました！\n");
            break;
        }
        printf("カードを引きますか？ (y/n): ");
        if (scanf_s(" %c", &choice) != 1)
        {
            while (getchar() != '\n')
                ; // バッファクリア
            continue;
        }
        if (choice == 'y' || choice == 'Y')
        {
            addCard(player, drawCard(deck));
        }
        else if (choice == 'n' || choice == 'N')
        {
            break;
        }
    }
}
 
void dealerTurn(Player *dealer, Deck *deck)
{
    while (calculateScore(dealer) < 17)
    {
        addCard(dealer, drawCard(deck));
    }
}
 
int getBet(Player *player)
{
    int bet;
    while (1)
    {
        printf("あなたの所持チップ: %d\n", player->chips);
        printf("掛け金を入力してください: ");
        if (scanf_s("%d", &bet) != 1)
        {
            while (getchar() != '\n')
                ; // バッファクリア
            printf("数字を入力してください。\n");
            continue;
        }
        if (bet > 0 && bet <= player->chips)
            break;
        printf("所持チップ以内の正しい掛け金を入力してください。\n");
    }
    return bet;
}
 
void determineWinner(Player *player, Player *dealer, int bet)
{
    int playerScore = calculateScore(player);
    int dealerScore = calculateScore(dealer);
 
    printf("\nあなたのスコア: %d\n", playerScore);
    printf("ディーラーのスコア: %d\n", dealerScore);
 
    if (playerScore > 21)
    {
        printf("あなたの負けです。\n");
        player->chips -= bet;
    }
    else if (dealerScore > 21 || playerScore > dealerScore)
    {
        printf("あなたの勝ちです！\n");
        player->chips += bet;
    }
    else if (playerScore < dealerScore)
    {
        printf("あなたの負けです。\n");
        player->chips -= bet;
    }
    else
    {
        printf("引き分けです。\n");
    }
 
    printf("現在の所持チップ: %d\n", player->chips);
}
 
int main()
{
    srand((unsigned int)time(NULL));
    char playAgain = 'y';
    Player player = {.count = 0, .chips = 1000};
 
    printf("ブラックジャックへようこそ！\n");
 
    while ((playAgain == 'y' || playAgain == 'Y') && player.chips > 0)
    {
        Deck deck;
        Player dealer = {.count = 0};
 
        initializeDeck(&deck);
        shuffleDeck(&deck);
 
        int bet = getBet(&player);
 
        player.count = 0;
        dealer.count = 0;
        addCard(&player, drawCard(&deck));
        addCard(&player, drawCard(&deck));
        addCard(&dealer, drawCard(&deck));
        addCard(&dealer, drawCard(&deck));
 
        playerTurn(&player, &deck);
        if (calculateScore(&player) <= 21)
            dealerTurn(&dealer, &deck);
 
        printf("\nディーラーの手札:\n");
        printHandColor(&dealer);
 
        determineWinner(&player, &dealer, bet);
 
        if (player.chips <= 0)
        {
            printf("所持チップがなくなりました。ゲーム終了です。\n");
            break;
        }
 
        printf("\nもう一度プレイしますか？ (y/n): ");
        scanf_s(" %c", &playAgain);
        while (getchar() != '\n')
            ; // バッファクリア
    }
 
    printf("ゲームを終了します。\n");
    return 0;
}