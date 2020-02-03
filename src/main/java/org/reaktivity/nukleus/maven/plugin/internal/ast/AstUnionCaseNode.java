/**
 * Copyright 2016-2020 The Reaktivity Project
 *
 * The Reaktivity Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.reaktivity.nukleus.maven.plugin.internal.ast;

import java.util.Objects;

public final class AstUnionCaseNode extends AstNode
{
    private final int value;
    private final AstStructMemberNode member;

    @Override
    public <R> R accept(
        Visitor<R> visitor)
    {
        return visitor.visitCase(this);
    }

    public int value()
    {
        return value;
    }

    public AstStructMemberNode member()
    {
        return member;
    }

    @Override
    public int hashCode()
    {
        return (member.hashCode() << 11) ^ value;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this)
        {
            return true;
        }

        if (!(o instanceof AstUnionCaseNode))
        {
            return false;
        }

        AstUnionCaseNode that = (AstUnionCaseNode) o;
        return this.value == that.value &&
                Objects.equals(this.member, that.member);
    }

    private AstUnionCaseNode(
        int value,
        AstStructMemberNode member)
    {
        this.value = value;
        this.member = member;
    }

    @Override
    public String toString()
    {
        return String.format("CASE [value=%d, member=%s]", value, member);
    }

    public static final class Builder extends AstNode.Builder<AstUnionCaseNode>
    {
        private int value;
        private AstStructMemberNode member;

        public Builder value(int value)
        {
            this.value = value;
            return this;
        }

        public Builder member(AstStructMemberNode member)
        {
            this.member = member;
            return this;
        }

        @Override
        public AstUnionCaseNode build()
        {
            return new AstUnionCaseNode(value, member);
        }
    }
}
