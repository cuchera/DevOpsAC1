package org.example.ac1.ranking.application;

import org.example.ac1.ranking.domain.PontuacaoRanking;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class RankingService {

    public List<PontuacaoRanking> classificar(
            List<PontuacaoRanking> pontuacoes) {

        return pontuacoes.stream()
                .sorted(Comparator.comparingInt(
                        PontuacaoRanking::getPontos
                ).reversed())
                .toList();
    }
}