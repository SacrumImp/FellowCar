<?php

    use Illuminate\Database\Migrations\Migration;
    use Illuminate\Database\Schema\Blueprint;
    use Illuminate\Support\Facades\Schema;

    class Communities extends Migration
    {
        /**
         * Run the migrations.
         *
         * @return void
         */
        public function up()
        {
            Schema::create('communities', function (Blueprint $table) {
                $table->increments('id');
                $table->string('name');
                $table->timestamp('dateOfCreation');
                $table->string('description');
                $table->string('location');
                $table->json('members');
            });
        }

        /**
         * Reverse the migrations.
         *
         * @return void
         */
        public function down()
        {
            Schema::dropIfExists('communities');
        }
    }
