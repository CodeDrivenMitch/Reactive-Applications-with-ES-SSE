import {defineStore} from "pinia";
import {ref} from "vue";
import {useResultStore} from "@/stores/result";


export const useAccountStore = defineStore(
    'account', () => {
        const {setResult} = useResultStore()
        const account = ref<string | null>(localStorage.getItem("account"))

        const createAccount = async () => {
            try {
                const r = await fetch("/api/account", {method: "POST"})
                const createdAccountId = await r.text();
                if(createdAccountId === "null") {
                    setResult(false, "No valid account ID received from backend")
                    return
                }
                account.value = createdAccountId
                localStorage.setItem("account", createdAccountId)
                setResult(true, `Account with id ${createdAccountId} successfully created!`)
            } catch (e) {
                setResult(false, e.message)
            }
        }

        const logout = () => {
            localStorage.removeItem('account')
            account.value = null
        }

        return {account, createAccount, logout}
    });