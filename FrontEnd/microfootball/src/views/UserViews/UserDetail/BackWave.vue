<template>
    <div class="background">
        <svg width="100%" height="120%" id="svg" viewBox="0 0 1440 400" xmlns="http://www.w3.org/2000/svg"
            class="transition duration-300 ease-in-out delay-150">
            <defs>
                <linearGradient id="gradient" x1="0%" y1="50%" x2="100%" y2="50%">
                    <stop offset="5%" stop-color="#8ed1fc"></stop>
                    <stop offset="95%" stop-color="#8ed1fc"></stop>
                </linearGradient>
            </defs>
            <path
                d="M 0,600 C 0,600 0,200 0,200 C 100.59330143540669,240.59330143540672 201.18660287081337,281.18660287081343 311,267 C 420.8133971291866,252.8133971291866 539.846889952153,183.8468899521531 627,172 C 714.153110047847,160.1531100478469 769.4258373205741,205.42583732057415 863,225 C 956.5741626794259,244.57416267942585 1088.4497607655503,238.44976076555025 1191,230 C 1293.5502392344497,221.55023923444975 1366.7751196172248,210.77511961722487 1440,200 C 1440,200 1440,600 1440,600 Z"
                stroke="none" stroke-width="0" fill="url(#gradient)" fill-opacity="0.53"
                class="transition-all duration-300 ease-in-out delay-150 path-0"></path>
            <defs>
                <linearGradient id="gradient" x1="0%" y1="50%" x2="100%" y2="50%">
                    <stop offset="5%" stop-color="#8ed1fc"></stop>
                    <stop offset="95%" stop-color="#8ed1fc"></stop>
                </linearGradient>
            </defs>
            <path
                d="M 0,600 C 0,600 0,400 0,400 C 120.22009569377991,393.2535885167464 240.44019138755982,386.50717703349284 335,369 C 429.5598086124402,351.49282296650716 498.45933014354057,323.22488038277515 573,336 C 647.5406698564594,348.77511961722485 727.7224880382777,402.59330143540666 838,413 C 948.2775119617223,423.40669856459334 1088.6507177033493,390.40191387559815 1194,381 C 1299.3492822966507,371.59808612440185 1369.6746411483255,385.7990430622009 1440,400 C 1440,400 1440,600 1440,600 Z"
                stroke="none" stroke-width="0" fill="url(#gradient)" fill-opacity="1"
                class="transition-all duration-300 ease-in-out delay-150 path-1"></path>
        </svg>
    </div>
</template>
  
<style scoped>
.background {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: -1;
}
</style>
  