import {defineStore} from "pinia";
import {ref} from "vue";


export const useResultStore = defineStore(
    'result', () => {
        const error = ref<string | null>(null)
        const success = ref<string | null>(null)


        const setResult = (ok: boolean, text: string) => {
            success.value = null
            error.value = null

            if (ok) {
                success.value = text
            } else {
                error.value = text
            }

            setTimeout(() => {
                success.value = null
                error.value = null
            }, 3000)
        }

        return {error, success, setResult}
    });