(function () {
    'use strict';

    $(document).ready(function () {
        $('[expand]').hover(
            function (event) {
                $(event.target).css('cursor', 'pointer');
            },
            function (event) {
                $(event.target).css('cursor', 'auto');
            }
        );

        $('[expand]').on('click', function (event) {
            var expandable = $(event.target).attr('expand');
            $('[expandable=' + expandable + ']').toggle();
        });

        $('[expandable]').hide();
    });
}());
