package ir.ac.ut.ece.ie.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleService {

    private static final ArticleService instance = new ArticleService();
    private List<Article> articles = new ArrayList<>();
    private String searchInput = null;

    private ArticleService() {
        articles.add(new Article(
                "Assessing and Understanding Creativity in  Large Language Models",
                "In the field of natural language processing, the rapid development of large language model (LLM) has attracted increasing attention. LLMs have shown a high level of creativity in various tasks, but the methods for assessing such creativity are inadequate. Assessment of LLM creativity needs to consider differences from humans, requiring multiple dimensional measurement while balancing accuracy and efficiency. This paper aims to establish an efficient framework for assessing the level of creativity in LLMs. By adapting the modified Torrance tests of creative thinking, the research evaluates the creative performance of various LLMs across 7 tasks, emphasizing 4 criteria including fluency, flexibility, originality, and elaboration. In this context, we develop a comprehensive dataset of 700 questions for testing and an LLM-based evaluation method. In addition, this study presents a novel analysis of LLMs′ responses to diverse prompts and role-play situations. We found that the creativity of LLMs primarily falls short in originality, while excelling in elaboration. In addition, the use of prompts and role-play settings of the model significantly influence creativity. Additionally, the experimental results also indicate that collaboration among multiple LLMs can enhance originality. Notably, our findings reveal a consensus between human evaluations and LLMs regarding the personality traits that influence creativity. The findings underscore the significant impact of LLM design on creativity and bridge artificial intelligence and human creativity, offering insights into LLMs′ creativity and potential applications.",
                "",
                2026
        ));

        articles.add(new Article(
                "SPARK: A System for Scientifically Creative Idea Generation",
                "Recently, large language models (LLMs) have shown promising abilities to generate novel research ideas in science, a direction which coincides with many foundational principles in computational creativity (CC). In light of these developments, we present an idea generation system named SPARK that couples retrievalaugmented idea generation using LLMs with a reviewer model named JUDGE trained on 600K scientific reviews from OpenReview. Our work is both a system demonstration and intended to inspire other CC researchers to explore grounding the generation and evaluation of scientific ideas within foundational CC principles. To this end, we release the annotated dataset used to train JUDGE, inviting other researchers to explore the use of LLMs for idea generation and creative evaluations.",
                "",
                2023
        ));

        articles.add(new Article(
                "Emergent Quantized Communication",
                "The field of emergent communication aims to understand the characteristics of communication as it emerges from artificial agents solving tasks that require information exchange. Communication with discrete messages is considered a desired characteristic, for both scientific and applied reasons. However, training a multi-agent system with discrete communication is not straightforward, requiring either reinforcement learning algorithms or relaxing the discreteness requirement via a continuous approximation such as the Gumbel-softmax. Both these solutions result in poor performance compared to fully continuous communication. In this work, we propose an alternative approach to achieve discrete communication – quantization of communicated messages. Using message quantization allows us to train the model end-to-end, achieving superior performance in multiple setups. Moreover, quantization is a natural framework that runs the gamut from continuous to discrete communication. Thus, it sets the ground for a broader view of multi-agent communication in the deep learning era.",
                "",
                2022
        ));

        articles.add(new Article(
                "So many design choices:  Improving and interpreting neural agent communication in signaling games",
                "Emergent language games are experimental  protocols designed to model how communication may arise among a group of agents. In  this paper, we focus on how to improve performances of neural agents playing a signaling  game: a sender is exposed to an image and  generates a sequence of symbols that is transmitted to a receiver, which uses it to distinguish  between two images, one that is semantically  related to the original image, and one that is not.  We consider multiple design choices, such as  pretraining the visual components of the agents,  introducing regularization terms, how to sample training items from the dataset, and we  study how these different choices impact the behavior and performances of the agents. To that  end, we introduce a number of automatic metrics to measure the properties of the emergent  languages. We find that some implementation  choices are always beneficial, and that the information that is conveyed by the agents’ messages is shaped not only by the game, but also  by the overall design of the agents as well as  seemingly unrelated implementation choices.",
                "",
                2023
        ));
        articles.add(new Article(
                "A Combinatorial Approach to Neural Emergent Communication",
                "Substantial research on deep learning-based emergent communication uses the referential game framework, specifically the Lewis signaling game, however we argue that successful communication in this game typically only need one or two symbols for target image classification because of a sampling pitfall in the training data. To address this issue, we provide a theoretical analysis and introduce a combinatorial algorithm SolveMinSym (SMS) to solve the symbolic complexity for classification, which is the minimum number of symbols in the message for successful communication. We use the SMS algorithm to create datasets with different symbolic complexity to empirically show that data with higher symbolic complexity increases the number of effective symbols in the emergent language.",
                "",
                2026
        ));
    }

    public static ArticleService getInstance() {
        return instance;
    }

    public void addArticle(Article article) {
        articles.add(article);
    }

    public List<Article> getArticles() {
        return articles;
    }

    public List<Article> getFilteredArticles() {
        if (searchInput == null)
            return articles.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        return articles
                .stream()
                .filter(article ->
                        article.getTitle().toLowerCase().contains(searchInput) ||
                        article.getAbstract().toLowerCase().contains(searchInput))
                .sorted((a, b) -> {
                    boolean aHasSearchInput = a.getTitle().toLowerCase().contains(searchInput);
                    boolean bHasSearchInput = b.getTitle().toLowerCase().contains(searchInput);

                    if (aHasSearchInput && !bHasSearchInput) {
                        return -1;
                    }
                    else if (!aHasSearchInput && bHasSearchInput) {
                        return 1;
                    }
                    return b.compareTo(a);
                })
                .collect(Collectors.toList());
    }


    public void setFilter(String searchInput) {
        this.searchInput = searchInput.toLowerCase();
    }
}
