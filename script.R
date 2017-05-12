setwd("/home/bdanglot/workspace/TestGenerator/")
install.packages("ggplot2", dep=T)
library("ggplot2")

stat<-read.table(file = "output/stat_1.data", header=T)
frame=data.frame(budget=c(stat$budget), success=c(stat$success), algo=c(levels(stat$algo)))
ggplot(data=frame, aes(x=budget,y=success)) + geom_line(aes(colour=algo))

stat<-read.table(file = "output/stat.data", header=T)
frame=data.frame(bound=c(stat$bound), success=c(stat$success), algo=c(levels(stat$algo)))
ggplot(data=frame, aes(x=bound,y=success)) + geom_line(aes(colour=algo))

frame=data.frame(bound=c(stat$bound), avg=c(stat$avg), algo=c(levels(stat$algo)))
a<-ggplot(data=frame, aes(x=bound,y=avg)) + geom_line(aes(colour=algo))
                                                        
data<-read.table("output/boxplot.data", header=T)
ggplot(data=boxplot_data, aes(x=algo,? y=consumedbudget, fill=algo)) + geom_boxplot()

RD<-read.table(file="output/fisher_RD.data")
HC<-read.table(file="output/fisher_HC.data")
fisher.test(rbind(HC,RD))

budget<-read.table("output/budget_h.data")
wilcox.test(budget$V2~budget$V1)
