param(
    [string]$Arquivo
)

$reset   = [char]27 + "[0m"
$verde   = [char]27 + "[32m"
$vermelho = [char]27 + "[31m"
$ciano   = [char]27 + "[36m"
$cinza   = [char]27 + "[90m"
$amarelo = [char]27 + "[33m"

$limite = 10

if (-not $Arquivo) {
    $progressoPath = Join-Path $PSScriptRoot "..\PROGRESSO.md"
    if (Test-Path $progressoPath) {
        $progresso = Get-Content $progressoPath -Raw
        if ($progresso -match '`(src/main/java/exercicios/[^`]+)`') {
            $Arquivo = Join-Path $PSScriptRoot "..\$($Matches[1])"
        }
    }
    if (-not $Arquivo) {
        Write-Host "$vermelho Nenhum arquivo informado e nao foi possivel detectar o exercicio atual.$reset"
        Write-Host "Uso: .\scripts\analisar.ps1 src\main\java\exercicios\...\ExercicioXX.java"
        exit 1
    }
}

$Arquivo = Resolve-Path $Arquivo -ErrorAction SilentlyContinue
if (-not $Arquivo) {
    Write-Host "$vermelho Arquivo nao encontrado.$reset"
    exit 1
}

Write-Host ""
Write-Host "$ciano   Analisando: $reset $(Split-Path $Arquivo -Leaf)"

$saida = lizard -C $limite $Arquivo 2>&1
$codigo = $LASTEXITCODE

$temWarnings = $false
if ($saida -match '!!!! Warnings') {
    $temWarnings = $true
}

Write-Host ""

$linhas = $saida -split "`n"
$naTabela = $false
$funcoes = @()

foreach ($linha in $linhas) {
    if ($linha -match '^\s*NLOC\s+CCN') {
        $naTabela = $true
        continue
    }
    if ($linha -match '^\d+ file analyzed') {
        $naTabela = $false
        continue
    }
    if ($naTabela -and $linha -match '^\s+\d+') {
        $funcoes += $linha.Trim()
    }
}

if ($funcoes.Count -gt 0) {
    Write-Host "  $ciano Metodo                        CCN                    $reset"
    Write-Host "  $cinza ------                        ---                    $reset"
    foreach ($f in $funcoes) {
        $partes = $f -split '\s+'
        if ($partes.Count -ge 2) {
            $nloc = $partes[0]
            $ccn  = $partes[1]
            $loc  = $partes[-1]
            $nome = ($loc -split '@')[0]

            $corCCN = if ([int]$ccn -le 5) { $verde }
                      elseif ([int]$ccn -le $limite) { $amarelo }
                      else { $vermelho }

            $barra = ""
            for ($i = 0; $i -lt [int]$ccn; $i++) { $barra += "|" }

            Write-Host ("  {0,-30} {1}{2}{3} {4}" -f $nome, $corCCN, $ccn, $reset, $cinza + $barra + $reset)
        }
    }
}

Write-Host ""

if ($temWarnings) {
    Write-Host "  ${vermelho}X COMPLEXO${reset} -- um ou mais metodos excedem o limite de CCN ${amarelo}${limite}${reset}"
    Write-Host ""
    exit 1
} else {
    Write-Host "  ${verde}V OK${reset} -- todos os metodos dentro do limite de CCN ${amarelo}${limite}${reset}"
    Write-Host ""
    exit 0
}
