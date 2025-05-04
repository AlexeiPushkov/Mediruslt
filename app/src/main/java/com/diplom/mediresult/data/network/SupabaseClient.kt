package com.diplom.mediresult.data.network

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

private const val SUPABASE_URL = "https://bdpapaqkxiujjrhowsvx.supabase.co"
private const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJkcGFwYXFreGl1ampyaG93c3Z4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDUzMTQwOTEsImV4cCI6MjA2MDg5MDA5MX0.Ii1tIY196yOLjpsX-btNBURJ6O3RsYX_Z3UuRxTGmaY"


object SupabaseClient{
    val supabase = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_ANON_KEY
    ) {
        install(Postgrest)
        install(Auth)
    }
}