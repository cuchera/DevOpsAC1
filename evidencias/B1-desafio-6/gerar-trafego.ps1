# Desafio 6 - gera trafego nos 3 fluxos observados no Grafana.
# Uso (na raiz do repositorio):
#   powershell -ExecutionPolicy Bypass -File evidencias\B1-desafio-6\gerar-trafego.ps1
#   powershell -ExecutionPolicy Bypass -File evidencias\B1-desafio-6\gerar-trafego.ps1 -Rodadas 40
param(
    [int]$Rodadas = 20,
    [string]$Base = "http://localhost:8081"
)

for ($i = 1; $i -le $Rodadas; $i++) {
    # D4: conclusao de curso (dispara o evento de notificacao)
    $corpo = @{ nome = "Aluno $i"; cursoConcluido = $true; mediaFinal = 8.5 } | ConvertTo-Json
    $aluno = Invoke-RestMethod -Method Post -Uri "$Base/api/alunos/processar-encerramento" `
        -ContentType "application/json" -Body $corpo

    # D3: recomendacao via servico de IA (503 quando o ai-service esta fora)
    try {
        Invoke-RestMethod -Method Post -Uri "$Base/api/alunos/$($aluno.id)/ia/recomendacao" | Out-Null
        $ia = 200
    } catch {
        $ia = $_.Exception.Response.StatusCode.value__
    }

    # D5: evento IoT encaminhado ao Core
    $sensor = @{ sensor = "temperatura-01"; temperatura = 25; unidade = "C"; status = "NORMAL" } | ConvertTo-Json
    Invoke-RestMethod -Method Post -Uri "$Base/api/sensores" -ContentType "application/json" -Body $sensor | Out-Null

    Write-Host ("Rodada {0,2}: conclusao aluno {1} = 201 | IA = {2} | IoT = 200" -f $i, $aluno.id, $ia)
    Start-Sleep -Milliseconds 500
}
