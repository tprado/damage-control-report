(function () {
    'use strict';

    $(document).ready(function () {
        $('[expand]').hover(
            function (event) {
                $(event.target).css('cursor', 'pointer');
                $(event.target).css('text-decoration', 'underline');
            },
            function (event) {
                $(event.target).css('cursor', 'auto');
                $(event.target).css('text-decoration', 'none');
            }
        );

        $('[expand]').on('click', function (event) {
            var expandable = $(event.target).attr('expand');
            $('[expandable=' + expandable + ']').toggle();
        });

        $('[expandable]').hide();
    });
}());
