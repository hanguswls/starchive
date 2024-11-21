import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { PrismaService } from './prisma/prisma.service';
import { CategoryService } from './category/category.service';
import { PostModule } from './post/post.module';

@Module({
  imports: [PostModule],
  controllers: [AppController],
  providers: [AppService, PrismaService, CategoryService],
})
export class AppModule {}
