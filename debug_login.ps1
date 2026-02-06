try {
    $body = @{
        email    = "admin@test.com"
        password = "admin123"
    } | ConvertTo-Json
    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/auth/login" -Method Post -ContentType "application/json" -Body $body
    Write-Output "LOGIN SUCCESS"
    $token = $response.data.accessToken
    Write-Output $token

    if ($token) {
        Write-Output "Syncing..."
        $syncResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/admin/animals/sync" -Method Post -Headers @{Authorization = "Bearer $token" } -Body @{days = 100; maxPages = 2 } -ContentType "application/x-www-form-urlencoded"
        Write-Output "Sync result:"
        Write-Output $syncResponse
    }
}
catch {
    Write-Output "LOGIN FAILED"
    if ($_.Exception.Response) {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $reader.ReadToEnd()
    }
    else {
        Write-Output $_.Exception.Message
    }
}
