#include <stdio.h>

int main() {
    int ch;
    int tabu = 0,newline = 0, at = 0;

    printf("文字を入力してください（qで終了）：\n");

        while (1) {
            ch = getchar();

        switch (ch) {
            case '\t':
                tabu++;
                break;
            case '\n':
                newline++;
                break;
            case '@':
                at++;
                break;
            case 'q':
                printf("タブ:%d回\n", tabu);
                printf("改行:%d回\n", newline);
                printf("   @:%d回\n", at);
                return 0;
            default:
                break;
        }
    }

    return 0;
}
