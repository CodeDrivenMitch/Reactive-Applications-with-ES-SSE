<script setup lang="ts">

import {onMounted, reactive} from "vue";

interface BalanceOverview {
    overview: BalanceItem[],
}

interface BalanceItem {
    accountId: string,
    balance: number,
}

const map = reactive<{ [id: string]: number }>({})

// setInterval(() => {
//     fetch('http://localhost:8080/overview').then(async r => {
//         let result = await r.json() as BalanceOverview;
//         result.overview.forEach(r => map[r.accountId] = r.balance)
//     })
// }, 50)

let eventSource = new EventSource('http://localhost:8080/stream');
eventSource.onmessage = (ev: any) => {
    let data = JSON.parse(ev.data);
    map[data.accountId] = data.balance
}

</script>

<template>
    <div class="container-fluid">
        <nav class="navbar navbar-expand-lg navbar-light bg-white">
            <div class="container-fluid px-3">
                <a class="navbar-brand" href="/"><img src="./assets/logo.svg" alt="" width="30" height="24">Axon SSE
                    Livecoding Demo</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarSupportedContent"
                        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>


            </div>
        </nav>

        <div class="bg-white mt-4 p-4">
            <table class="table table-responsive">
                <thead>
                <tr>
                    <th class="text-end">Account</th>
                    <th class="text-end">Balance</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(item, id) in map">
                    <td class="text-end">{{id}}</td>
                    <td class="text-end">{{item.toFixed(2)}}</td>
                </tr>
                </tbody>
            </table>

        </div>
    </div>
</template>
