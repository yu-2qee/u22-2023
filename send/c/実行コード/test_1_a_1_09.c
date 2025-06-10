#include <stdio.h>

int main() {
    float weight, moon_weight;

    printf("weight = ");
    scanf_s("%f", &weight);

    moon_weight = weight * 0.17;

    printf("The weight on the surface of the \"moon\" = %.2f kg\n", moon_weight);

    return 0;
}
