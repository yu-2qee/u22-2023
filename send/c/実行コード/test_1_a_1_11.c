#include <stdio.h>

int main() {
    float kyo, speed;
    int num;

    printf("計算する回数を入力してください：");
    scanf_s("%d", &num);

    for (int i=1 ; i <= num;i++) {
        printf("\n【%d回目】\n", i);

        printf("距離(km)を入力してください：");
        scanf_s("%f", &kyo);

        printf("平均速度(km/h)を入力してください：");
        scanf_s("%f", &speed);

        if (speed == 0) {
            printf("速度が0では計算できねぇ。\n");
            continue; 
        }

        printf("所要時間は %.1f 時間です。\n", kyo / speed);
    }

    return 0;
}
