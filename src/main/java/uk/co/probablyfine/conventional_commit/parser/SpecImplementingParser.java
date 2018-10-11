package uk.co.probablyfine.conventional_commit.parser;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

import static uk.co.probablyfine.conventional_commit.parser.CommitBuilder.builder;

@BuildParseTree
public class SpecImplementingParser extends BaseParser<CommitBuilder> {

    public Rule Commit() {
        return Sequence(
            Type(),
            Optional(Scope()),
            Delimiter(),
            Sequence(
                NoneOf(" "),
                ZeroOrMore(ANY)
            )
        );
    }


    Rule Type() {
        return Sequence(
            OneOrMore(
                CharRange('A', 'z')
            ),
            push(builder().type(match()))
        );
    }

    Rule Delimiter() {
        return String(": ");
    }

    Rule Scope() {
        return Sequence(
            Ch('('),
            OneOrMore(NoneOf(")")),
            push(pop().scope(match())),
            Ch(')')
        );
    }
}