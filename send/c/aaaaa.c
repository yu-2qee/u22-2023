#include <stdio.h>
 
int main() {
    int num = 0;
    float f, m, o, p;
 
    do {
        printf("\n----------------------------\n");
        printf("１．フィートからメートルへ\n");
        printf("２．メートルからフィートへ\n");
        printf("３．オンスからポンドへ\n");
        printf("４．ポンドからオンスへ\n");
        printf("５．終了\n");
        printf("番号を選んでください：");
        scanf_s("%d", &num);  
 
        switch (num) {
        case 1:
            printf("何フィートですか？：");
            scanf_s("%f", &f);
            printf("%.2f フィートは %.2f メートルです\n", f, f * 0.3048);
            break;
 
        case 2:
            printf("何メートルですか？：");
            scanf_s("%f", &m);
            printf("%.2f メートルは %.2f フィートです\n", m, m / 0.3048);
            break;
 
        case 3:
            printf("何オンスですか？：");
            scanf_s("%f", &o);
            printf("%.2f オンスは %.2f ポンドです\n", o, o / 16.0);
            break;
 
        case 4:
            printf("何ポンドですか？：");
            scanf_s("%f", &p);
            printf("%.2f ポンドは %.2f オンスです\n", p, p * 16.0);
            break;
 
        case 5:
            printf("終了します。\n");
            break;
 
        default:
            printf("1～5の番号を入力してください。\n");
            break;
        }
 
    } while (num != 5);
 
    return 0;
}