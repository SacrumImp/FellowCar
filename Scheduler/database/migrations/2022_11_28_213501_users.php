<?php

    use Illuminate\Database\Migrations\Migration;
    use Illuminate\Database\Schema\Blueprint;
    use Illuminate\Support\Facades\Schema;

    class Users extends Migration
    {
        /**
         * Run the migrations.
         *
         * @return void
         */
        public function up()
        {
            Schema::create('users', function (Blueprint $table) {
                $table->increments('user_id');
                $table->timestamps();
                $table->string('name');
                $table->string('lastName');
                $table->string('email');
                $table->string('password');
                $table->string('phone');
                $table->string('profilePicture');
                $table->string('passport');
                $table->string('driversLicence');
                $table->string('rating');
            });
        }

        /**
         * Reverse the migrations.
         *
         * @return void
         */
        public function down()
        {
            Schema::dropIfExists('users');
        }
    }
