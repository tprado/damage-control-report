(function () {
    'use strict';

    $(document).ready(function () {
        $('[expand]').on('click', function (event) {
            var expandable = $(event.target).attr('expand');
            $('[expandable=' + expandable + ']').toggle();
        });

        $('[expandable]').hide();
    });
}());
