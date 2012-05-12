package spock.damagecontrol

class HtmlSpecDefinitionFormatter {

    private final SpecDefinition specDefinition

    HtmlSpecDefinitionFormatter(specDefinition) {
        this.specDefinition = specDefinition
    }

    def format() {
        String result = specDefinition.sourceCode

        SourceCodeNormalizer stringLiteral = new SourceCodeNormalizer(/('.*')|(".*")/, 'string_literal')
        SourceCodeNormalizer comments = new SourceCodeNormalizer(/\/\*(.*)\*\//, 'comments')

        result = stringLiteral.normalize(result)
        result = comments.normalize(result)

        result = result.replaceAll(/[ ]{2,}/, { it.replaceAll(' ', '&nbsp;') })

        int lineCount = 1
        result = "<span class='line-number'>${lineCount++}</span>" + result.replaceAll(/\n/, { "<br/>${it}<span class='line-number'>${lineCount++}</span>" })


        result = stringLiteral.denormalize(result, { "<span class='string-literal'>${it}</span>" })
        result = comments.denormalize(result, { "<span class='comments'>${it}</span>" })

        result = "<div id='spec-definition'>\n${result}\n</div>"

        return result
    }
}
